package com.thebrownfoxx.unfocus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thebrownfoxx.unfocus.dependency.dependencies
import com.thebrownfoxx.unfocus.ui.component.CommandInput
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerScreen
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerViewModel
import com.thebrownfoxx.unfocus.ui.screen.timer.commands
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(onFlashTaskbar: () -> Unit = {}) {
    val viewModel = viewModel {
        TimerViewModel(dependencies.presenceAnnouncer)
    }

    LaunchedEffect(viewModel.flashTaskbar) {
        viewModel.flashTaskbar.collect {
            onFlashTaskbar()
        }
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val announcePresence by viewModel.announcePresence.collectAsStateWithLifecycle()

    Box {
        TimerScreen(
            state = state,
            onTimerButtonClick = viewModel::onTimerButtonClick,
            announcePresence = announcePresence,
            onAnnouncePresenceToggle = viewModel::toggleAnnouncePresence,
        )

        CommandInput(
            commands = viewModel.commands,
            contentColor = state.colors.contentColor,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(32.dp),
        )
    }
}