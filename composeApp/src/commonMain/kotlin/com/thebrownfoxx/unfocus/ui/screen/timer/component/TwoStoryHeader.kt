package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TwoStoryHeader(
    topText: String,
    baseText: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = topText,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = baseText,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}