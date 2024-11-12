package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors

@Composable
fun TimerBackground(
    state: TimerUiState,
    modifier: Modifier = Modifier,
) {
    val fillProgress by animateFloatAsState(
        targetValue = state.fillProgress,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
    )

    val colors = state.colors
    val backgroundColor by animateColorAsState(colors.backgroundColor)
    val fillColor by animateColorAsState(colors.fillColor)

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