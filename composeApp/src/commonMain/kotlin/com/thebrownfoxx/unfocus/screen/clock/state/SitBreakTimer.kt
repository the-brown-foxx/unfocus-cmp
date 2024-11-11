package com.thebrownfoxx.unfocus.screen.clock.state

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val SitBreakTimerGroup = TimerGroup(
    intro = SitBreakIntro,
    timer = SitBreakTimer(),
    expired = SitBreakExpired,
)

private val sitBreakDuration = 30.seconds

data object SitBreakIntro : TimerIntro {
    override val duration = sitBreakDuration
}

data class SitBreakTimer(
    override val duration: Duration = sitBreakDuration,
    override val running: Boolean = true,
) : Timer

data object SitBreakExpired : TimerExpired