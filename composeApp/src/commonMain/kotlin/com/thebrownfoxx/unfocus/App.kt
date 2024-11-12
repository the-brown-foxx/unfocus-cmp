package com.thebrownfoxx.unfocus

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thebrownfoxx.unfocus.domain.TestPhaseDurationProvider
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerScreen
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerViewModel
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    UnfocusTheme {
//        var testMode by remember { mutableStateOf(false) }
        val viewModel = viewModel { TimerViewModel(TestPhaseDurationProvider) }
        val state = viewModel.uiState.collectAsStateWithLifecycle()
        TimerScreen(
            state = state.value,
            onTimerButtonClick = viewModel::onTimerButtonClick,
        )
    }
}