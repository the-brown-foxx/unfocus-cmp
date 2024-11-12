package com.thebrownfoxx.unfocus.ui.extension

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun ColorScheme.containerColorFor(contentColor: Color): Color =
    when (contentColor) {
        onPrimary -> primary
        onSecondary -> secondary
        onTertiary -> tertiary
        onBackground -> background
        onError -> error
        onPrimaryContainer -> primaryContainer
        onSecondaryContainer -> secondaryContainer
        onTertiaryContainer -> tertiaryContainer
        onErrorContainer -> errorContainer
        inverseOnSurface -> inverseSurface
        onSurface -> surface
        onSurfaceVariant -> surfaceVariant
        else -> Color.Unspecified
    }

@Composable
fun containerColorFor(contentColor: Color) =
    MaterialTheme.colorScheme.containerColorFor(contentColor)