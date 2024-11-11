package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val BreakClockColors
    @Composable get() = with(MaterialTheme.colorScheme) {
        ClockColors(
            backgroundColor = surface,
            fillColor = surfaceContainer,
            contentColor = onSurface,
            clockButtonContainerColor = secondary,
            expiredBackgroundColor = secondary,
            expiredContentColor = onSecondary,
            expiredClockButtonContainerColor = secondaryContainer,
        )
    }