package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.domain.phase.DefaultPhaseDefinition
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetEventHandler
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ShownConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiPhaseQueue
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        ConfigurationSheet(
            state = ShownConfigurationSheetState(
                focusDuration = 20.minutes,
                eyeBreakDuration = 20.seconds,
                sitBreakDuration = 1.minutes,
                fullRestDuration = 30.minutes,
                eyeBreaks = 2,
                sitBreaks = 1,
                phaseQueue = DefaultPhaseDefinition.queue.toUiPhaseQueue(),
            ),
            eventHandler = ConfigurationSheetEventHandler.Blank,
        )
    }
}