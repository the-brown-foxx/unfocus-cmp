package com.thebrownfoxx.unfocus.domain.phase

import com.thebrownfoxx.unfocus.domain.phase.Phase.EyeBreak
import com.thebrownfoxx.unfocus.domain.phase.Phase.Focus
import com.thebrownfoxx.unfocus.domain.phase.Phase.FullRest
import com.thebrownfoxx.unfocus.domain.phase.Phase.SitBreak
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class PhaseDefinition(
    val durations: PhaseDurations = PhaseDurations.Default,
    val cycle: PhaseCycle = PhaseCycle.Default,
) {
    val Phase.duration: Duration
        get() = when (this) {
            Focus -> durations.focusDuration
            EyeBreak -> durations.eyeBreakDuration
            SitBreak -> durations.sitBreakDuration
            FullRest -> durations.fullRestDuration
        }

    val queue: List<Phase> = cycle.queue

    val strideDuration: Duration
        get() = queue
            .dropLast(1)
            .map { it.duration }
            .reduce { accumulator, duration -> accumulator + duration }
}

fun PhaseDefinition(
    strideDuration: Duration,
    eyeBreakDuration: Duration,
    sitBreakDuration: Duration,
    fullRestDuration: Duration,
    eyeBreaks: Int,
    sitBreaks: Int,
): PhaseDefinition {
    val semiStrideDuration = (strideDuration - sitBreakDuration * sitBreaks) / (sitBreaks + 1)
    val focusDuration = (semiStrideDuration - eyeBreakDuration * eyeBreaks) / (eyeBreaks + 1)

    return PhaseDefinition(
        durations = PhaseDurations(
            focusDuration = focusDuration,
            eyeBreakDuration = eyeBreakDuration,
            sitBreakDuration = sitBreakDuration,
            fullRestDuration = fullRestDuration,
        ),
        cycle = PhaseCycle(eyeBreaks = eyeBreaks, sitBreaks = sitBreaks),
    )
}

data class PhaseDurations(
    val focusDuration: Duration,
    val eyeBreakDuration: Duration,
    val sitBreakDuration: Duration,
    val fullRestDuration: Duration,
) {
    companion object {
        val Default = PhaseDurations(
            focusDuration = 20.minutes,
            eyeBreakDuration = 20.seconds,
            sitBreakDuration = 1.minutes,
            fullRestDuration = 30.minutes,
        )
    }
}

data class PhaseCycle(
    val eyeBreaks: Int,
    val sitBreaks: Int,
) {
    companion object {
        val Default = PhaseCycle(
            eyeBreaks = 2,
            sitBreaks = 1,
        )
    }

    private val sitBreakCycle = Focus + (EyeBreak + Focus) * eyeBreaks
    val queue =  sitBreakCycle + (SitBreak + sitBreakCycle) * sitBreaks + FullRest

    private operator fun Phase.plus(other: Phase) = listOf(this, other)
    private operator fun Phase.plus(other: List<Phase>) = listOf(this) + other
    private operator fun List<Phase>.times(other: Int) = Array(other) { this }.toList().flatten()
}