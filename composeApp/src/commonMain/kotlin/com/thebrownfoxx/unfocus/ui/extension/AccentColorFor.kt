package com.thebrownfoxx.unfocus.ui.extension

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun ColorScheme.accentColorFor(containerColor: Color): Color =
    when (containerColor) {
        primaryContainer -> primary
        secondaryContainer -> secondary
        tertiaryContainer -> tertiary
        errorContainer -> error
        surface -> secondary
        surfaceContainer -> secondary
        else -> Color.Unspecified
    }

@Composable
fun accentColorFor(containerColor: Color) =
    MaterialTheme.colorScheme.accentColorFor(containerColor)