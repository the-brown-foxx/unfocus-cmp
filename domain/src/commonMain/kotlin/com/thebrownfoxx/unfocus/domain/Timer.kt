package com.thebrownfoxx.unfocus.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Timer(
    private val phaseDurationProvider: PhaseDurationProvider,
) : PhaseDurationProvider by phaseDurationProvider {
    private var phaseIterator = Phase.queue.iterator()

    private val _state = MutableStateFlow<TimerState>(Instruction(nextPhase()))
    val state = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    
    private val ticker: Ticker = Ticker(
        tickInterval = 10.milliseconds,
        startImmediately = false,
    )

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
        ticker.run { elapsed ->
            val state = _state.value
            if (state is Instruction && !state.paused && state.duration < Instruction.MaxDuration) {
                _state.value = state.copy(duration = state.duration + elapsed)
            } else {
                ticker.cancel()
                _state.value = MainTimer(state.phase)
            }
        }
    }

    private fun resetInstruction() {
        ticker.run { elapsed ->
            val state = _state.value
            if (state is Instruction && state.paused && state.duration > Duration.ZERO) {
                _state.value = state.copy(duration = state.duration - elapsed)
            } else {
                ticker.cancel()
            }
        }
    }

    private fun runMainTimer() {
        ticker.run { elapsed ->
            val state = _state.value
            if (state is MainTimer && !state.paused && state.duration > Duration.ZERO) {
                _state.value = state.copy(duration = state.duration - elapsed)
            } else {
                ticker.cancel()
                _state.value = Expired(state.phase)
            }
        }
    }

    private fun pauseMainTimer() {
        ticker.cancel()
    }
}

fun PhaseDurationProvider.Timer() = Timer(this)