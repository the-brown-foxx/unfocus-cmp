package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object DefaultPhaseDurationProvider : PhaseDurationProvider {
    override val Phase.duration: Duration
        get() = when (this) {
            Phase.Focus -> 20.minutes
            Phase.EyeBreak -> 20.seconds
            Phase.SitBreak -> 1.minutes
            Phase.FullRest -> 30.minutes
        }
}