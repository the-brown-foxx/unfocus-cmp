package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.unfocus.ui.screen.timer.component.WindowBar
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview
@Composable
private fun HiddenPreview() {
    UnfocusTheme {
        WindowBar(
            onMinimize = {},
            onClose = {},
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UnfocusTheme {
        WindowBar(
            onMinimize = {},
            onClose = {},
            hovered = true,
        )
    }
}