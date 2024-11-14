package com.thebrownfoxx.unfocus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thebrownfoxx.unfocus.dependency.dependencies
import com.thebrownfoxx.unfocus.domain.DefaultPhaseDurationProvider
import com.thebrownfoxx.unfocus.domain.TestPhaseDurationProvider
import com.thebrownfoxx.unfocus.ui.component.Command
import com.thebrownfoxx.unfocus.ui.component.CommandInput
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerScreen
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerViewModel
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors
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
        var show by remember { mutableStateOf(false) }

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

        val state by viewModel.uiState.collectAsStateWithLifecycle()

        Box {
            TimerScreen(
                state = state,
                onTimerButtonClick = viewModel::onTimerButtonClick,
                onMinimize = onMinimize,
                onClose = onClose,
            )

            CommandInput(
                command = code,
                onCommandChange = { code = it },
                commands = listOf(
                    Command("test") { testMode = true },
                    Command("reset") { testMode = false },
                    Command("show") { show = true },
                    Command("hide") { show = false },
                ),
                contentColor = state.colors.contentColor,
                modifier = Modifier
                    .alpha(if (show) 1f else 0f)
                    .align(Alignment.BottomStart)
                    .padding(32.dp),
            )
        }
    }
}