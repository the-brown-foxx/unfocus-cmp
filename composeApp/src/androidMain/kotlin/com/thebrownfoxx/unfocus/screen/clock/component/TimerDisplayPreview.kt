package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.screen.clock.state.FocusTimerGroup
import com.thebrownfoxx.unfocus.screen.clock.state.Intro
import com.thebrownfoxx.unfocus.theme.UnfocusTheme

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        TimerDisplay(
            clockState = Intro,
            colors = FocusTimerGroup.colors,
            onRunningToggle = {},
        )
    }
}