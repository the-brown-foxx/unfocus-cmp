package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.component.CircleButton
import com.thebrownfoxx.unfocus.component.Spacer
import com.thebrownfoxx.unfocus.component.circleButtonColors
import com.thebrownfoxx.unfocus.extension.accentColorFor
import com.thebrownfoxx.unfocus.extension.containerColorFor
import com.thebrownfoxx.unfocus.extension.toHhSs
import kotlin.time.Duration

@Composable
fun TimerIntroDisplay(
    header: String,
    duration: Duration,
    onStart: () -> Unit,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .safeDrawingPadding()
                .padding(32.dp)
                .fillMaxSize(),
        ) {
            Header(text = header)
            Spacer(height = 8.dp)
            Duration(duration = duration)
            Spacer(height = 16.dp)
            StartButton(onStart = onStart, contentColor = contentColor)
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
private fun StartButton(
    onStart: () -> Unit,
    contentColor: Color,
) {

    CircleButton(
        icon = {
            Icon(
                imageVector = Icons.TwoTone.PlayArrow,
                contentDescription = "Start",
            )
        },
        colors = circleButtonColors(accentColorFor(containerColorFor(contentColor))),
        onClick = onStart,
    )
}
