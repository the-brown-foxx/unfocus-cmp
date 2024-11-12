package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object TestPhaseDurationProvider : PhaseDurationProvider {
    override val Phase.duration: Duration
        get() = when (this) {
            Phase.Focus -> 5.seconds
            Phase.EyeBreak -> 2.seconds
            Phase.SitBreak -> 3.seconds
            Phase.FullRest -> 10.seconds
        }
}