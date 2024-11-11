package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun TimerBackground(
    progress: Float,
    backgroundColor: Color,
    fillColor: Color,
    modifier: Modifier = Modifier,
) {
    val fillProgress by animateFloatAsState(targetValue = progress)

    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            color = backgroundColor,
            size = size,
        )
        drawRect(
            color = fillColor,
            size = size.copy(height = size.height * fillProgress),
            topLeft = Offset(x = 0f, y = size.height * (1f - fillProgress)),
        )
    }
}