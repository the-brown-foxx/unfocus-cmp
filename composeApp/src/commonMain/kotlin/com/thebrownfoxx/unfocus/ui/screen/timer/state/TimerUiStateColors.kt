package com.thebrownfoxx.unfocus.ui.screen.timer.state

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val TimerUiState.colors: TimerColors
    @Composable get() {
        val state = this
        return with(MaterialTheme.colorScheme) {
            when (state.type) {
                TimerType.Intro -> introColors
                PhaseTimerType.Focus -> if (expired) focusExpiredColors else focusColors
                is BreakTimerType -> if (expired) breakExpiredColors else breakColors
                PhaseTimerType.Rest -> if (expired) restExpiredColors else restColors
            }
        }
    }

private val ColorScheme.introColors
    get() = TimerColors(
        backgroundColor = primary,
        fillColor = primary,
        contentColor = onPrimary,
        clockButtonContainerColor = primaryContainer,
    )

private val ColorScheme.focusColors
    get() = TimerColors(
        backgroundColor = surface,
        fillColor = primaryContainer,
        contentColor = onSurface,
        clockButtonContainerColor = primary,
    )

private val ColorScheme.focusExpiredColors
    get() = TimerColors(
        backgroundColor = primary,
        fillColor = primary,
        contentColor = onPrimary,
        clockButtonContainerColor = primaryContainer,
    )

private val ColorScheme.breakColors
    get() = TimerColors(
        backgroundColor = surface,
        fillColor = secondaryContainer,
        contentColor = onSurface,
        clockButtonContainerColor = secondary,
    )

private val ColorScheme.breakExpiredColors
    get() = TimerColors(
        backgroundColor = secondary,
        fillColor = secondary,
        contentColor = onSecondary,
        clockButtonContainerColor = secondaryContainer,
    )

private val ColorScheme.restColors
    get() = TimerColors(
        backgroundColor = surface,
        fillColor = tertiaryContainer,
        contentColor = onSurface,
        clockButtonContainerColor = tertiary,
    )

private val ColorScheme.restExpiredColors
    get() = TimerColors(
        backgroundColor = tertiary,
        fillColor = tertiary,
        contentColor = onTertiary,
        clockButtonContainerColor = tertiaryContainer,
    )