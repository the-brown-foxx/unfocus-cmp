package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pause
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material.icons.twotone.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.component.ButtonColors
import com.thebrownfoxx.unfocus.ui.component.CircleButton
import com.thebrownfoxx.unfocus.ui.component.Spacer
import com.thebrownfoxx.unfocus.ui.component.buttonColors
import com.thebrownfoxx.unfocus.ui.extension.toHhSs
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerButtonState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.colors
import com.thebrownfoxx.unfocus.ui.screen.timer.state.text
import kotlin.time.Duration

@Composable
fun TimerDisplay(
    state: TimerUiState,
    onTimerButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = state.colors
    val contentColor by animateColorAsState(colors.contentColor)
    val clockButtonContainerColor by animateColorAsState(colors.clockButtonContainerColor)
    val clockButtonContentColor by animateColorAsState(contentColorFor(clockButtonContainerColor))
    val buttonColors = buttonColors(
        containerColor = clockButtonContainerColor,
        contentColor = clockButtonContentColor,
    )

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .safeDrawingPadding()
                .padding(32.dp)
                .fillMaxSize(),
        ) {
            AnimatedContent(targetState = state.header) { header ->
                Header(text = header.text)
            }
            Spacer(height = 8.dp)
            Duration(
                duration = state.duration,
                expired = state.expired,
            )
            Spacer(height = 16.dp)
            TimerButton(
                state = state.timerButtonState,
                onClick = onTimerButtonClick,
                buttonColors = buttonColors,
            )
        }
    }
}

@Composable
private fun Header(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun Duration(
    duration: Duration,
    expired: Boolean,
) {
    val style = MaterialTheme.typography.displayLarge
    Row {
        AnimatedVisibility(visible = expired) {
            Text(
                text = "-",
                style = style,
            )
        }
        Text(
            text = duration.toHhSs(),
            style = style,
        )
    }
}

@Composable
private fun TimerButton(
    state: TimerButtonState,
    onClick: () -> Unit,
    buttonColors: ButtonColors,
) {
    CircleButton(
        colors = buttonColors,
        onClick = onClick,
    ) { TimerButtonIcon(state = state) }
}

@Composable
private fun TimerButtonIcon(state: TimerButtonState) {
    AnimatedContent(targetState = state) {
        when (it) {
            TimerButtonState.Run -> Icon(
                imageVector = Icons.TwoTone.PlayArrow,
                contentDescription = "Start",
            )

            TimerButtonState.Pause -> Icon(
                imageVector = Icons.TwoTone.Pause,
                contentDescription = "Pause",
            )

            TimerButtonState.Stop -> Icon(
                imageVector = Icons.TwoTone.Stop,
                contentDescription = "Stop",
            )
        }
    }
}