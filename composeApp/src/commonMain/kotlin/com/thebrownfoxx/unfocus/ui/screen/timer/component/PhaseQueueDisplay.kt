package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.screen.timer.state.BreakTimerType
import com.thebrownfoxx.unfocus.ui.screen.timer.state.PhaseTimerType

@Composable
fun PhaseQueueIndicator(
    phaseIndex: Int,
    phaseProgress: Float,
    phaseQueue: List<PhaseTimerType>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        for ((index, phase) in phaseQueue.withIndex()) {
            val progress = when {
                phaseIndex < index -> 0f
                phaseIndex > index -> 1f
                else -> phaseProgress
            }
            when (phase) {
                PhaseTimerType.Focus -> FocusIndicator(progress = progress)
                BreakTimerType.Eye -> EyeBreakIndicator(progress = progress)
                BreakTimerType.Sit -> SitBreakIndicator(progress = progress)
                PhaseTimerType.Rest -> RestIndicator(progress = progress)
            }
        }
    }
}

@Composable
fun FocusIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        width = 32.dp,
    )
}

@Composable
fun EyeBreakIndicator(progress: Float) {
    PhaseIndicator(progress = progress)
}

@Composable
fun SitBreakIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        height = 16.dp,
    )
}

@Composable
fun RestIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        height = 24.dp,
    )
}

@Composable
fun PhaseIndicator(
    progress: Float,
    width: Dp = indicatorMinSize,
    height: Dp = indicatorMinSize,
) {
    val foreground = indicatorForeground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(width = width, height = height)
            .background(indicatorBackground)
            .drawWithContent {
                drawContent()
                drawRect(
                    color = foreground,
                    size = size.copy(width = size.width * progress),
                )
            }
    )
}

private val indicatorMinSize = 8.dp

private val indicatorBackground
    @Composable get() = LocalContentColor.current.copy(alpha = 0.2f)

private val indicatorForeground
    @Composable get() = LocalContentColor.current.copy(alpha = 0.4f)