package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme
import kotlin.time.Duration.Companion.minutes

@Preview
@Composable
private fun Preview() {
    UnfocusTheme {
        DurationField(
            duration = 20.minutes,
            onDurationChange = {},
            leadingIcon = Icons.Outlined.Timer,
            label = "Duration",
            modifier = Modifier.padding(16.dp),
        )
    }
}