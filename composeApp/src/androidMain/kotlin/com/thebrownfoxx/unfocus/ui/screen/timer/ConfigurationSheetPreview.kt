package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.domain.phase.PhaseCycle
import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition
import com.thebrownfoxx.unfocus.domain.phase.PhaseDurations
import com.thebrownfoxx.unfocus.ui.screen.timer.component.ConfigurationSheet
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetEventHandler
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        ConfigurationSheet(
            state = PhaseDefinition(
                durations = PhaseDurations(
                    focusDuration = 20.minutes,
                    eyeBreakDuration = 20.seconds,
                    sitBreakDuration = 1.minutes,
                    fullRestDuration = 30.minutes,
                ),
                cycle = PhaseCycle(
                    eyeBreaks = 2,
                    sitBreaks = 1,
                ),
            ).toConfigurationSheetState(),
            eventHandler = ConfigurationSheetEventHandler.Blank,
        )
    }
}