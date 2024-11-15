package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration

class UserPhaseDurationProvider(private val durations: UserPhaseDurations) : PhaseDurationProvider {
    override val Phase.duration: Duration
        get() = when (this) {
            Phase.Focus -> durations.focusDuration
            Phase.EyeBreak -> durations.eyeBreakDuration
            Phase.SitBreak -> durations.sitBreakDuration
            Phase.FullRest -> durations.fullRestDuration
        }
}

data class UserPhaseDurations(
    val focusDuration: Duration,
    val eyeBreakDuration: Duration,
    val sitBreakDuration: Duration,
    val fullRestDuration: Duration,
)