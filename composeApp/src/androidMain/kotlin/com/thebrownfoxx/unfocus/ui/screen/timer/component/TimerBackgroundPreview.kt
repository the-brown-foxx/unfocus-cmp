package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.domain.phase.DefaultPhaseDefinition
import com.thebrownfoxx.unfocus.ui.screen.timer.state.getIntroTimerUiState
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        TimerBackground(state = getIntroTimerUiState(DefaultPhaseDefinition))
    }
}