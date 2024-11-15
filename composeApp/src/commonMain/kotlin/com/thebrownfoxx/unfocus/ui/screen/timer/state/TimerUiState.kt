package com.thebrownfoxx.unfocus.ui.screen.timer.state

import kotlin.time.Duration

data class TimerUiState(
    val phaseIndex: Int,
    val phaseProgress: Float,
    val phaseQueue: List<PhaseTimerType>,
    val type: TimerType,
    val header: TimerHeader,
    val fillProgress: Float,
    val duration: Duration,
    val paused: Boolean,
    val timerButtonState: TimerButtonState,
    val expired: Boolean,
)

sealed interface TimerType {
    data object Intro : TimerType
}

sealed interface PhaseTimerType : TimerType {
    data object Focus : PhaseTimerType
    data object Rest : PhaseTimerType
}

enum class BreakTimerType : PhaseTimerType {
    Eye,
    Sit,
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