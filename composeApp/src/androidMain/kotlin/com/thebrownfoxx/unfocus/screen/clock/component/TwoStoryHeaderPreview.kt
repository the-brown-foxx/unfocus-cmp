package com.thebrownfoxx.unfocus.screen.clock.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.theme.UnfocusTheme

@Preview
@Composable
private fun Preview() {
    UnfocusTheme {
        TwoStoryHeader(
            topText = "Ready to",
            baseText = "Skibidi?",
            modifier = Modifier.padding(16.dp),
        )
    }
}