package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.component.ButtonColors
import com.thebrownfoxx.unfocus.component.CircleButton
import com.thebrownfoxx.unfocus.component.Spacer
import com.thebrownfoxx.unfocus.extension.toHhSs
import com.thebrownfoxx.unfocus.screen.clock.state.ClockRunningState
import kotlin.time.Duration

@Composable
fun TimerDisplay(
    header: String,
    duration: Duration?,
    runningState: ClockRunningState,
    onRunningToggle: () -> Unit,
    contentColor: Color,
    buttonColors: ButtonColors,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .safeDrawingPadding()
                .padding(32.dp)
                .fillMaxSize(),
        ) {
            AnimatedContent(targetState = duration) { targetDuration ->
                Column {
                    Header(text = header)
                    if (targetDuration != null) {
                        Spacer(height = 8.dp)
                        Duration(duration = targetDuration)
                    }
                }
            }
            Spacer(height = 16.dp)
            ClockButton(
                runningState = runningState,
                onRunningToggle = onRunningToggle,
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
private fun Duration(duration: Duration) {
    Text(
        text = duration.toHhSs(),
        style = MaterialTheme.typography.displayLarge,
    )
}

@Composable
private fun ClockButton(
    runningState: ClockRunningState,
    onRunningToggle: () -> Unit,
    buttonColors: ButtonColors,
) {
    CircleButton(
        icon = { ClockButtonIcon(runningState = runningState) },
        colors = buttonColors,
        onClick = onRunningToggle,
    )
}

@Composable
private fun ClockButtonIcon(runningState: ClockRunningState) {
    AnimatedContent(targetState = runningState) {
        when (runningState) {
            ClockRunningState.Running -> Icon(
                imageVector = Icons.TwoTone.Pause,
                contentDescription = "Pause",
            )

            ClockRunningState.Paused -> Icon(
                imageVector = Icons.TwoTone.PlayArrow,
                contentDescription = "Start",
            )

            ClockRunningState.Expired -> Icon(
                imageVector = Icons.TwoTone.Stop,
                contentDescription = "Stop",
            )
        }
    }
}