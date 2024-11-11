package com.thebrownfoxx.unfocus.screen.clock

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.thebrownfoxx.unfocus.screen.clock.component.TimerBackground
import com.thebrownfoxx.unfocus.screen.clock.component.TimerDisplay
import com.thebrownfoxx.unfocus.screen.clock.state.ClockColors
import com.thebrownfoxx.unfocus.screen.clock.state.ClockState
import com.thebrownfoxx.unfocus.screen.clock.state.TimerExpired

@Composable
fun ClockScreen(
    state: ClockState,
    onRunningToggle: () -> Unit,
    colors: ClockColors,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        when (state) {
            is TimerExpired -> colors.expiredBackgroundColor
            else -> colors.backgroundColor
        }
    )

    Box(modifier = modifier) {
        TimerBackground(
            progress = state.progress,
            backgroundColor = backgroundColor,
            fillColor = colors.fillColor,
        )
        TimerDisplay(
            clockState = state,
            colors = colors,
            onRunningToggle = onRunningToggle,
        )
    }
}