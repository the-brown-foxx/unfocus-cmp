package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object TestPhaseDurationProvider : PhaseDurationProvider {
    override val Phase.duration: Duration
        get() = when (this) {
            Phase.Focus -> 10.seconds
            Phase.EyeBreak -> 3.seconds
            Phase.SitBreak -> 5.seconds
            Phase.FullRest -> 1.minutes
        }
}