package com.thebrownfoxx.unfocus.screen.clock.state

import kotlin.time.Duration

sealed interface ClockState

sealed interface TimerIntro : ClockState {
    val duration: Duration
}

sealed interface Timer : ClockState {
    val duration: Duration
    val running: Boolean
}

sealed interface TimerExpired : ClockState

data class TimerGroup(
    val intro: TimerIntro,
    val timer: Timer,
    val expired: TimerExpired,
) {
    fun toList() = listOf(intro, timer, expired)
}