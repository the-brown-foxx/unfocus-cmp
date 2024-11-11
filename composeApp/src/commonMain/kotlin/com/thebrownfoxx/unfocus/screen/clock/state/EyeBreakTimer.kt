package com.thebrownfoxx.unfocus.screen.clock.state

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val EyeBreakTimerGroup = TimerGroup(
    intro = EyeBreakIntro,
    timer = EyeBreakTimer(),
    expired = EyeBreakExpired,
)

private val eyeBreakDuration = 20.seconds

data object EyeBreakIntro : TimerIntro {
    override val duration = eyeBreakDuration
}

data class EyeBreakTimer(
    override val duration: Duration = eyeBreakDuration,
    override val running: Boolean = true,
) : Timer

data object EyeBreakExpired : TimerExpired