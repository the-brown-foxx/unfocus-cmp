package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.domain.DefaultPhaseDurationProvider
import com.thebrownfoxx.unfocus.ui.screen.timer.state.IntroTimerUiState
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview
@Composable
private fun Preview() {
    UnfocusTheme {
        TimerScreen(
            state = DefaultPhaseDurationProvider.IntroTimerUiState,
            onTimerButtonClick = {},
            onMinimize = {},
            onClose = {},
        )
    }
}