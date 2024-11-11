package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.theme.UnfocusTheme
import kotlin.time.Duration.Companion.minutes

@Preview(device = "spec:width=673dp,height=841dp")
@Composable
private fun Preview() {
    UnfocusTheme {
        TimerBackground(
            remainingDuration = 20.minutes,
            fullDuration = 30.minutes,
            backgroundColor = MaterialTheme.colorScheme.surface,
            fillColor = MaterialTheme.colorScheme.primary,
        )
    }
}