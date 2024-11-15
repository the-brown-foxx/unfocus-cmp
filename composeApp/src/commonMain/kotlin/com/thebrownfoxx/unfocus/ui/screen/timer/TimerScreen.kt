package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.screen.timer.component.PhaseQueueIndicator
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TimerBackground
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TimerDisplay
import com.thebrownfoxx.unfocus.ui.screen.timer.component.TopBar
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors

@Composable
fun TimerScreen(
    state: TimerUiState,
    onTimerButtonClick: () -> Unit,
    announcePresence: Boolean,
    onAnnouncePresenceToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides state.colors.contentColor) {
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
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}