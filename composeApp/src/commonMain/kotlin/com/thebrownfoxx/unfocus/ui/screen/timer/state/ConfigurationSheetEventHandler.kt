package com.thebrownfoxx.unfocus.ui.screen.timer.state

import kotlin.time.Duration

class ConfigurationSheetEventHandler(
    val onShowConfigurationSheet: () -> Unit,
    val onHideConfigurationSheet: () -> Unit,
    val onFocusDurationChange: (Duration) -> Unit,
    val onEyeBreakDurationChange: (Duration) -> Unit,
    val onSitBreakDurationChange: (Duration) -> Unit,
    val onFullRestDurationChange: (Duration) -> Unit,
    val onEyeBreaksChange: (Int) -> Unit,
    val onSitBreaksChange: (Int) -> Unit,
    val onSaveConfiguration: () -> Unit,
) {
    companion object {
        val Blank = ConfigurationSheetEventHandler(
            onShowConfigurationSheet = {},
            onHideConfigurationSheet = {},
            onFocusDurationChange = {},
            onEyeBreakDurationChange = {},
            onSitBreakDurationChange = {},
            onFullRestDurationChange = {},
            onEyeBreaksChange = {},
            onSitBreaksChange = {},
            onSaveConfiguration = {},
        )
    }

    fun copy(
        onShowConfigurationSheet: () -> Unit = this.onShowConfigurationSheet,
        onHideConfigurationSheet: () -> Unit = this.onHideConfigurationSheet,
        onFocusDurationChange: (Duration) -> Unit = this.onFocusDurationChange,
        onEyeBreakDurationChange: (Duration) -> Unit = this.onEyeBreakDurationChange,
        onSitBreakDurationChange: (Duration) -> Unit = this.onSitBreakDurationChange,
        onFullRestDurationChange: (Duration) -> Unit = this.onFullRestDurationChange,
        onEyeBreaksChange: (Int) -> Unit = this.onEyeBreaksChange,
        onSitBreaksChange: (Int) -> Unit = this.onSitBreaksChange,
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
        onSaveConfiguration = onSaveConfiguration,
    )
}