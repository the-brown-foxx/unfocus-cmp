package com.thebrownfoxx.unfocus.screen.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.screen.clock.state.FocusIntro
import com.thebrownfoxx.unfocus.theme.UnfocusTheme

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        ClockScreen(state = FocusIntro)
    }
}