package com.thebrownfoxx.unfocus.ui.screen.timer.state

import kotlin.time.Duration

data class TimerUiState(
    val type: TimerType,
    val header: TimerHeader,
    val fillProgress: Float,
    val duration: Duration,
    val paused: Boolean,
    val timerButtonState: TimerButtonState,
    val expired: Boolean,
)

enum class TimerType {
    Intro,
    Focus,
    Break,
    Rest,
}

enum class TimerHeader {
    Intro,
    FocusPaused,
    FocusInstruction,
    FocusTimer,
    FocusExpired,
    EyeBreakPaused,
    EyeBreakInstruction,
    EyeBreakTimer,
    EyeBreakExpired,
    SitBreakPaused,
    SitBreakInstruction,
    SitBreakTimer,
    SitBreakExpired,
    FullRestPaused,
    FullRestInstruction,
    FullRestTimer,
    FullRestExpired,
}

enum class TimerButtonState {
    Run,
    Pause,
    Stop,
}