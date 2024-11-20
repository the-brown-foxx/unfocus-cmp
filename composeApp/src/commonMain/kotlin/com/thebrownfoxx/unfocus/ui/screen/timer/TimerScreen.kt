package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thebrownfoxx.unfocus.ui.component.PhaseQueueIndicator
import com.thebrownfoxx.unfocus.ui.component.ProvideContentColor
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TimerBackground
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TimerDisplay
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TopBar
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetEventHandler
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors

@Composable
fun TimerScreen(
    state: TimerUiState,
    onTimerButtonClick: () -> Unit,
    announcePresence: Boolean,
    onAnnouncePresenceToggle: () -> Unit,
    configurationSheetState: ConfigurationSheetState,
    configurationSheetEventHandler: ConfigurationSheetEventHandler,
    modifier: Modifier = Modifier,
) {
    ProvideContentColor(state.colors.contentColor) {
        Box(modifier = modifier) {
            TimerBackground(state = state)
            TimerDisplay(
                state = state,
                onTimerButtonClick = onTimerButtonClick,
            )
            TopBar(
                announcePresence = announcePresence,
                onAnnouncePresenceToggle = onAnnouncePresenceToggle,
            )
            PhaseQueueIndicator(
                phaseIndex = state.phaseIndex,
                phaseProgress = state.phaseProgress,
                phaseQueue = state.phaseQueue,
                onClick = configurationSheetEventHandler.onShowConfigurationSheet,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
            ConfigurationSheet(
                state = configurationSheetState,
                eventHandler = configurationSheetEventHandler,
            )
        }
    }
}