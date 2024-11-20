package com.thebrownfoxx.unfocus.domain.timer

import com.thebrownfoxx.unfocus.domain.phase.Phase
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class TimerState(
    val phaseIndex: Int,
    val phaseQueue: List<Phase>,
    val values: TimerStateValues = Instruction(),
) {
    val phase = phaseQueue[phaseIndex]

    val next get() = TimerState(
        phaseIndex = if (phaseIndex == phaseQueue.lastIndex) 0 else phaseIndex + 1,
        phaseQueue = phaseQueue,
        values = Instruction(),
    )
}

sealed interface TimerStateValues {
    val paused: Boolean
}

data class Instruction(
    val duration: Duration = Duration.ZERO,
    override val paused: Boolean = false,
) : TimerStateValues {
    companion object {
        val MaxDuration = 2.seconds
    }
}

data class MainTimer(
    val duration: Duration,
    override val paused: Boolean = false,
) : TimerStateValues

data class Expired(
    val duration: Duration = Duration.ZERO,
) : TimerStateValues {
    override val paused = false
}