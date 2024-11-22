package com.thebrownfoxx.unfocus.ui.screen.timer.state

import kotlin.time.Duration

interface ConfigurationSheetState

data class ShownConfigurationSheetState(
    val phaseQueue: List<PhaseTimerType>,
    val focusDuration: Duration,
    val eyeBreakDuration: Duration,
    val sitBreakDuration: Duration,
    val fullRestDuration: Duration,
    val eyeBreaks: Int,
    val sitBreaks: Int,
    val strideDuration: Duration,
    val lockedField: LockedConfigurationSheetField = LockedConfigurationSheetField.FocusDuration,
) : ConfigurationSheetState

enum class LockedConfigurationSheetField {
    FocusDuration,
    StrideDuration,
}

data object HiddenConfigurationSheetState : ConfigurationSheetState