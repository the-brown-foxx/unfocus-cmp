package com.thebrownfoxx.unfocus.screen.clock.state

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

val FocusTimerGroup = TimerGroup(
    intro = FocusIntro,
    timer = FocusTimer(),
    expired = FocusExpired,
)

private val focusDuration = 20.minutes

data object FocusIntro : TimerIntro {
    override val duration = focusDuration
}

data class FocusTimer(
    override val duration: Duration = focusDuration,
    override val running: Boolean = true,
) : Timer

data object FocusExpired : TimerExpired