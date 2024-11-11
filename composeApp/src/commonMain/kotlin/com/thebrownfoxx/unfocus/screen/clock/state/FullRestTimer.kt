package com.thebrownfoxx.unfocus.screen.clock.state

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

val FullRestTimerGroup = TimerGroup(
    intro = FullRestIntro,
    timer = FullRestTimer(),
    expired = FullRestExpired,
)

private val fullRestDuration = 20.minutes

data object FullRestIntro : TimerIntro {
    override val duration = fullRestDuration
}

data class FullRestTimer(
    override val duration: Duration = fullRestDuration,
    override val running: Boolean = true,
) : Timer

data object FullRestExpired : TimerExpired