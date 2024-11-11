package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.time.Duration

@Composable
fun TimerBackground(
    remainingDuration: Duration,
    fullDuration: Duration,
    backgroundColor: Color,
    fillColor: Color,
    modifier: Modifier = Modifier,
) {
    val remainingPercent =
        remainingDuration.inWholeMilliseconds.toFloat() / fullDuration.inWholeMilliseconds

    val fillPercent by animateFloatAsState(targetValue = remainingPercent)

    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            color = backgroundColor,
            size = size,
        )
        drawRect(
            color = fillColor,
            size = size.copy(height = size.height * fillPercent),
            topLeft = Offset(x = 0f, y = size.height * (1f - fillPercent)),
        )
    }
}