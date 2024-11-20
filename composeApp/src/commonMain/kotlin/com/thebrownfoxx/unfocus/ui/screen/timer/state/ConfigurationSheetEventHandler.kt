package com.thebrownfoxx.unfocus.ui.screen.timer.state

import kotlin.time.Duration

class ConfigurationSheetEventHandler(
    val onShowConfigurationSheet: () -> Unit,
    val onHideConfigurationSheet: () -> Unit,
    val onEyeBreaksChange: (Int) -> Unit,
    val onSitBreaksChange: (Int) -> Unit,
    val onFocusDurationChange: (Duration) -> Unit,
    val onEyeBreakDurationChange: (Duration) -> Unit,
    val onSitBreakDurationChange: (Duration) -> Unit,
    val onFullRestDurationChange: (Duration) -> Unit,
    val onStrideDurationChange: (Duration) -> Unit,
    val onSaveConfiguration: () -> Unit,
) {
    companion object {
        val Blank = ConfigurationSheetEventHandler(
            onShowConfigurationSheet = {},
            onHideConfigurationSheet = {},
            onEyeBreaksChange = {},
            onSitBreaksChange = {},
            onFocusDurationChange = {},
            onEyeBreakDurationChange = {},
            onSitBreakDurationChange = {},
            onFullRestDurationChange = {},
            onStrideDurationChange = {},
            onSaveConfiguration = {},
        )
    }

    fun copy(
        onShowConfigurationSheet: () -> Unit = this.onShowConfigurationSheet,
        onHideConfigurationSheet: () -> Unit = this.onHideConfigurationSheet,
        onEyeBreaksChange: (Int) -> Unit = this.onEyeBreaksChange,
        onSitBreaksChange: (Int) -> Unit = this.onSitBreaksChange,
        onFocusDurationChange: (Duration) -> Unit = this.onFocusDurationChange,
        onEyeBreakDurationChange: (Duration) -> Unit = this.onEyeBreakDurationChange,
        onSitBreakDurationChange: (Duration) -> Unit = this.onSitBreakDurationChange,
        onFullRestDurationChange: (Duration) -> Unit = this.onFullRestDurationChange,
        onStrideDurationChange: (Duration) -> Unit = this.onStrideDurationChange,
        onSaveConfiguration: () -> Unit = this.onSaveConfiguration,
    ) = ConfigurationSheetEventHandler(
        onShowConfigurationSheet = onShowConfigurationSheet,
        onHideConfigurationSheet = onHideConfigurationSheet,
        onFocusDurationChange = onFocusDurationChange,
        onEyeBreakDurationChange = onEyeBreakDurationChange,
        onSitBreakDurationChange = onSitBreakDurationChange,
        onFullRestDurationChange = onFullRestDurationChange,
        onEyeBreaksChange = onEyeBreaksChange,
        onSitBreaksChange = onSitBreaksChange,
        onStrideDurationChange = onStrideDurationChange,
        onSaveConfiguration = onSaveConfiguration,
    )
}