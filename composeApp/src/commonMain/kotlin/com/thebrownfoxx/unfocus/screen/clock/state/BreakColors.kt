package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val BreakClockColors
    @Composable get() = ClockColors(
        backgroundColor = MaterialTheme.colorScheme.surface,
        fillColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        clockButtonContainerColor = MaterialTheme.colorScheme.secondary,
        expiredBackgroundColor = MaterialTheme.colorScheme.secondary,
        expiredContentColor = MaterialTheme.colorScheme.onSecondary,
    )