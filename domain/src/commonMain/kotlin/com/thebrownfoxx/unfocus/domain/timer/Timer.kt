package com.thebrownfoxx.unfocus.domain.timer

import com.thebrownfoxx.unfocus.domain.Ticker
import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Timer(private val phaseDefinition: PhaseDefinition) {
    private val _state = MutableStateFlow(
        TimerState(
            phaseIndex = 0,
            phaseQueue = phaseDefinition.queue,
        )
    )
    val state = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val ticker: Ticker = Ticker(
        tickInterval = 10.milliseconds,
        startImmediately = false,
    )

    init {
        coroutineScope.launch {
            _state.collectLatest { state ->
                when (state.values) {
                    is Instruction -> when {
                        state.values.paused -> resetInstruction()
                        else -> runInstruction()
                    }

                    is MainTimer -> when {
                        state.values.paused -> pauseMainTimer()
                        else -> runMainTimer()
                    }

                    is Expired -> runExpiredTimer()
                }
            }
        }
    }

    fun toggleRunning() {
        _state.update {
            when (it.values) {
                is Instruction -> it.copy(values = it.values.copy(paused = !it.values.paused))
                is MainTimer -> it.copy(values = it.values.copy(paused = !it.values.paused))
                is Expired -> it.next
            }
        }
    }

    fun skipPhase() {
        _state.update { it.next.copy(values = Instruction(paused = true)) }
    }

    fun cancel() {
        ticker.cancel()
    }

    private fun runInstruction() {
        ticker.run { elapsed ->
            _state.update {
                with(it) {
                    val values =
                        if (
                            values is Instruction &&
                            !values.paused &&
                            values.duration < Instruction.MaxDuration
                        ) {
                            values.copy(duration = values.duration + elapsed)
                        } else {
                            ticker.cancel()
                            MainTimer(duration = with(phaseDefinition) { phase.duration })
                        }
                    it.copy(values = values)
                }
            }
        }
    }

    private fun resetInstruction() {
        ticker.run { elapsed ->
            val values = _state.value.values
            if (values is Instruction && values.paused && values.duration > Duration.ZERO) {
                val newValues = values.copy(duration = values.duration - elapsed)
                _state.update { it.copy(values = newValues) }
            } else {
                ticker.cancel()
            }
        }
    }

    private fun runMainTimer() {
        ticker.run { elapsed ->
            _state.update {
                with(it) {
                    val values = if (
                        values is MainTimer &&
                        !values.paused &&
                        values.duration > Duration.ZERO
                    ) {
                        values.copy(duration = values.duration - elapsed)
                    } else {
                        ticker.cancel()
                        Expired()
                    }
                    it.copy(values = values)
                }
            }
        }
    }

    private fun pauseMainTimer() {
        ticker.cancel()
    }

    private fun runExpiredTimer() {
        ticker.run { elapsed ->
            val state = _state.value.values
            if (state is Expired) {
                val newValues = state.copy(duration = state.duration + elapsed)
                _state.update { it.copy(values = newValues) }
            } else {
                ticker.cancel()
            }
        }
    }
}