package com.thebrownfoxx.unfocus.screen.clock

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.unfocus.screen.clock.component.TimerBackground
import com.thebrownfoxx.unfocus.screen.clock.component.TimerIntroDisplay
import com.thebrownfoxx.unfocus.screen.clock.state.ClockState
import kotlin.time.Duration.Companion.minutes

@Composable
fun ClockScreen(
    state: ClockState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        TimerBackground(
            remainingDuration = 20.minutes,
            fullDuration = 20.minutes,
            backgroundColor = MaterialTheme.colorScheme.surface,
            fillColor = MaterialTheme.colorScheme.surface,
        )
        TimerIntroDisplay(
            header = "Lock in?",
            duration = 20.minutes,
            onStart = {},
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}