package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.screen.timer.state.BreakTimerType
import com.thebrownfoxx.unfocus.ui.screen.timer.state.PhaseTimerType

@Composable
fun PhaseQueueIndicator(
    phaseIndex: Int,
    phaseProgress: Float,
    phaseQueue: List<PhaseTimerType>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = phaseQueue,
        modifier = modifier,
        transitionSpec = {
            fadeIn() + slideInHorizontally { it } togetherWith
                    fadeOut(spring(stiffness = Spring.StiffnessHigh))
        },
    ) {
        PhaseQueueRow(
            it = it,
            phaseIndex = phaseIndex,
            phaseProgress = phaseProgress,
            onClick = onClick,
        )
    }
}

@Composable
private fun PhaseQueueRow(
    phaseIndex: Int,
    phaseProgress: Float,
    it: List<PhaseTimerType>,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .horizontalScroll(rememberScrollState(), enabled = false)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .clip(CircleShape)
            .height(32.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
    ) {
        for ((index, phase) in it.withIndex()) {
            val progress = when {
                phaseIndex < index -> 0f
                phaseIndex > index -> 1f
                else -> phaseProgress
            }
            when (phase) {
                PhaseTimerType.Focus -> FocusIndicator(progress = progress)
                BreakTimerType.Eye -> EyeBreakIndicator(progress = progress)
                BreakTimerType.Sit -> SitBreakIndicator(progress = progress)
                PhaseTimerType.Rest -> {
                    /*RestIndicator(progress = progress)*/
                }
            }
        }
    }
}

@Composable
private fun FocusIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        modifier = Modifier
            .width(32.dp)
            .height(indicatorMinSize)
    )
}

@Composable
private fun EyeBreakIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        modifier = Modifier.size(indicatorMinSize)
    )
}

@Composable
private fun SitBreakIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        modifier = Modifier
            .width(indicatorMinSize)
            .height(16.dp),
    )
}

@Composable
private fun FullRestIndicator(progress: Float) {
    PhaseIndicator(
        progress = progress,
        modifier = Modifier
            .width(indicatorMinSize)
            .height(24.dp),
    )
}

@Composable
fun PhaseIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val foreground = indicatorForeground

    Box(
        modifier = modifier
            .clip(CircleShape)
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