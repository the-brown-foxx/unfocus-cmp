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
import com.thebrownfoxx.unfocus.ui.component.ProvideContentColor
import com.thebrownfoxx.unfocus.ui.component.WindowDraggableArea
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerScreen
import com.thebrownfoxx.unfocus.ui.screen.timer.TimerViewModel
import com.thebrownfoxx.unfocus.ui.screen.timer.commands
import com.thebrownfoxx.unfocus.ui.screen.timer.component.WindowBar
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetEventHandler
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onFlashTaskbar: () -> Unit = {},
    onMinimize: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    val viewModel = viewModel {
        TimerViewModel(dependencies.presenceManager, dependencies.configurator)
    }

    with(viewModel) {
        LaunchedEffect(flashTaskbar) {
            flashTaskbar.collect {
                onFlashTaskbar()
            }
        }

        val state by uiState.collectAsStateWithLifecycle()
        val announcePresence by announcePresence.collectAsStateWithLifecycle()
        val configurationState by configurationSheetState.collectAsStateWithLifecycle()

        Box {
            TimerScreen(
                state = state,
                onTimerButtonClick = ::onTimerButtonClick,
                announcePresence = announcePresence,
                onAnnouncePresenceToggle = ::toggleAnnouncePresence,
                configurationSheetState = configurationState,
                configurationSheetEventHandler = ConfigurationSheetEventHandler(
                    onShowConfigurationSheet = ::showConfigurationSheet,
                    onHideConfigurationSheet = ::hideConfigurationSheet,
                    onEyeBreaksChange = ::updateEyeBreaks,
                    onSitBreaksChange = ::updateSitBreaks,
                    onFocusDurationChange = ::updateFocusDuration,
                    onEyeBreakDurationChange = ::updateEyeBreakDuration,
                    onSitBreakDurationChange = ::updateSitBreakDuration,
                    onFullRestDurationChange = ::updateFullRestDuration,
                    onStrideDurationChange = ::updateStrideDuration,
                    onSaveConfiguration = ::saveConfiguration,
                )
            )

            ProvideContentColor(state.colors.contentColor) {
                WindowDraggableArea {
                    WindowBar(
                        onMinimize = onMinimize,
                        onClose = onClose,
                    )
                }
            }

            CommandInput(
                commands = commands,
                contentColor = state.colors.contentColor,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(32.dp),
            )
        }
    }
}