package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.domain.phase.DefaultPhaseDefinition
import com.thebrownfoxx.unfocus.ui.component.PhaseQueueIndicator
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiPhaseQueue
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview
@Composable
private fun UnfilledPreview() {
    UnfocusTheme {
        PhaseQueueIndicator(
            phaseIndex = 0,
            phaseProgress = 0f,
            phaseQueue = DefaultPhaseDefinition.queue.toUiPhaseQueue(),
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}