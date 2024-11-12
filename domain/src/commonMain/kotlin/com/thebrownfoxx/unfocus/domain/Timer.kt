package com.thebrownfoxx.unfocus.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import java.util.Timer as Ticker
import kotlin.concurrent.timer as ticker

class Timer {
    private var phaseIterator = Phase.queue.iterator()

    private val _state = MutableStateFlow<TimerState>(Instruction(nextPhase()))
    val state = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var lastTick: Instant = Instant.DISTANT_PAST
    private var ticker: Ticker? = null

    init {
        coroutineScope.launch {
            _state.collectLatest { state ->
                when (state) {
                    is Instruction -> when {
                        state.paused -> resetInstruction()
                        else -> runInstruction()
                    }

                    is MainTimer -> when {
                        state.paused -> pauseMainTimer()
                        else -> runMainTimer()
                    }

                    is Expired -> {}
                }
            }
        }
    }

    fun toggleRunning() {
        _state.updateAndGet {
            when (it) {
                is Instruction -> it.copy(paused = !it.paused)
                is MainTimer -> it.copy(paused = !it.paused)
                is Expired -> Instruction(nextPhase())
            }
        }
    }

    private fun nextPhase(): Phase {
        if (!phaseIterator.hasNext()) {
            phaseIterator = Phase.queue.iterator()
        }
        return phaseIterator.next()
    }

    private fun runInstruction() {
        runTicker { elapsed ->
            val state = _state.value
            if (state is Instruction && !state.paused && state.duration < Instruction.MaxDuration) {
                _state.value = state.copy(duration = state.duration + elapsed)
            } else {
                cancelTicker()
                _state.value = MainTimer(state.phase)
            }
        }
    }

    private fun resetInstruction() {
        runTicker { elapsed ->
            val state = _state.value
            if (state is Instruction && state.paused && state.duration > Duration.ZERO) {
                _state.value = state.copy(duration = state.duration - elapsed)
            } else {
                cancelTicker()
            }
        }
    }

    private fun runMainTimer() {
        runTicker { elapsed ->
            val state = _state.value
            if (state is MainTimer && !state.paused && state.duration > Duration.ZERO) {
                _state.value = state.copy(duration = state.duration - elapsed)
            } else {
                cancelTicker()
                _state.value = Expired(state.phase)
            }
        }
    }

    private fun pauseMainTimer() {
        cancelTicker()
    }

    private fun runTicker(onTick: (elapsed: Duration) -> Unit) {
        cancelTicker()
        lastTick = Clock.System.now()

        val tickInterval = 10.milliseconds
        ticker = ticker(
            initialDelay = tickInterval.inWholeMilliseconds,
            period = tickInterval.inWholeMilliseconds,
        ) {
            val tick = Clock.System.now()
            onTick(tick - lastTick)
            lastTick = tick
        }
    }

    private fun cancelTicker() {
        ticker?.cancel()
        ticker?.purge()
    }
}