package com.thebrownfoxx.unfocus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thebrownfoxx.unfocus.dependency.dependencies
import com.thebrownfoxx.unfocus.domain.DefaultPhaseDurationProvider
import com.thebrownfoxx.unfocus.domain.TestPhaseDurationProvider
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerScreen
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerViewModel
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onMinimize: () -> Unit = {},
    onClose: () -> Unit = {},
) {
    UnfocusTheme {
        var code by remember { mutableStateOf("") }
        var testMode by remember { mutableStateOf(false) }

        val viewModel = viewModel(key = testMode.toString()) {
            val phaseDurationProvider = when {
                testMode -> TestPhaseDurationProvider
                else -> DefaultPhaseDurationProvider
            }
            TimerViewModel(
                phaseDurationProvider,
                dependencies.presenceAnnouncer,
            )
        }

        val state = viewModel.uiState.collectAsStateWithLifecycle()

        CodeInput(
            code = code,
            onCodeChange = { code = it },
            onTestModeChange = { testMode = it },
        )

        TimerScreen(
            state = state.value,
            onTimerButtonClick = viewModel::onTimerButtonClick,
            onMinimize = onMinimize,
            onClose = onClose,
        )
    }
}