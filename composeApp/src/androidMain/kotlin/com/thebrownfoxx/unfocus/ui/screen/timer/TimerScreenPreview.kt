package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.domain.DefaultPhaseDefinition
import com.thebrownfoxx.unfocus.ui.screen.timer.state.getIntroTimerUiState
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview
@Composable
private fun Preview() {
    UnfocusTheme {
        TimerScreen(
            state = getIntroTimerUiState(DefaultPhaseDefinition),
            onTimerButtonClick = {},
            onMinimize = {},
            onClose = {},
        )
    }
}