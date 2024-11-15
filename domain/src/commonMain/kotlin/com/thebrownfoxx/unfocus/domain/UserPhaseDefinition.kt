package com.thebrownfoxx.unfocus.domain

import com.thebrownfoxx.unfocus.domain.Phase.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class UserPhaseDefinition(
    val durations: UserPhaseDurations = UserPhaseDurations.Default,
    val cycle: UserPhaseCycle = UserPhaseCycle.Default,
) : PhaseDefinition {
    override val Phase.duration: Duration
        get() = when (this) {
            Focus -> durations.focusDuration
            EyeBreak -> durations.eyeBreakDuration
            SitBreak -> durations.sitBreakDuration
            FullRest -> durations.fullRestDuration
        }

    override val queue: List<Phase>
        get() {
            val sitBreakCycle = Focus + (EyeBreak + Focus) * cycle.eyeBreaks
            return sitBreakCycle + (SitBreak + sitBreakCycle) * cycle.sitBreaks + FullRest
        }

    private operator fun Phase.plus(other: Phase) = listOf(this, other)
    private operator fun Phase.plus(other: List<Phase>) = listOf(this) + other
    private operator fun List<Phase>.times(other: Int) = Array(other) { this }.toList().flatten()
}

data class UserPhaseDurations(
    val focusDuration: Duration,
    val eyeBreakDuration: Duration,
    val sitBreakDuration: Duration,
    val fullRestDuration: Duration,
) {
    companion object {
        val Default = UserPhaseDurations(
            focusDuration = 20.minutes,
            eyeBreakDuration = 20.seconds,
            sitBreakDuration = 1.minutes,
            fullRestDuration = 30.minutes,
        )
    }
}

data class UserPhaseCycle(
    val eyeBreaks: Int,
    val sitBreaks: Int,
) {
    companion object {
        val Default = UserPhaseCycle(
            eyeBreaks = 2,
            sitBreaks = 1,
        )
    }
}