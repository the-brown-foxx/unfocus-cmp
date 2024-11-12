package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TimerBackground
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TimerDisplay
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerUiState

@Composable
fun TimerScreen(
    state: TimerUiState,
    onTimerButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        TimerBackground(
            state = state,
        )
        TimerDisplay(
            state = state,
            onTimerButtonClick = onTimerButtonClick,
        )
    }
}