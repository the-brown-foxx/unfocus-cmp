package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

sealed interface TimerState {
    val phase: Phase
    val paused: Boolean
}

data class Instruction(
    override val phase: Phase,
    val duration: Duration = Duration.ZERO,
    override val paused: Boolean = false,
) : TimerState {
    companion object {
        val MaxDuration = 2.seconds
    }
}

data class MainTimer(
    override val phase: Phase,
    val duration: Duration,
    override val paused: Boolean = false,
) : TimerState

fun PhaseDurationProvider.MainTimer(phase: Phase) = MainTimer(
    phase = phase,
    duration = phase.duration,
)

data class Expired(
    override val phase: Phase,
    val duration: Duration = Duration.ZERO,
) : TimerState {
    override val paused = false
}