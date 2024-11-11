package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.component.buttonColors
import com.thebrownfoxx.unfocus.screen.clock.state.ClockRunningState
import com.thebrownfoxx.unfocus.theme.UnfocusTheme
import kotlin.time.Duration.Companion.minutes

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        TimerDisplay(
            header = "Skibidi",
            duration = 20.minutes,
            runningState = ClockRunningState.Paused,
            onRunningToggle = {},
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            buttonColors = buttonColors(),
        )
    }
}