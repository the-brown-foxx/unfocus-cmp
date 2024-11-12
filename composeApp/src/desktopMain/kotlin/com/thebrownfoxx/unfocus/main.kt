package com.thebrownfoxx.unfocus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

fun main() = application {
    val windowState = rememberWindowState()

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "Unfocus",
        undecorated = true,
        transparent = true,
    ) {
        window.minimumSize = Dimension(600, 400)

        WindowDraggableArea(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
            Box {
                App(
                    onMinimize = { windowState.isMinimized = !windowState.isMinimized },
                    onClose = ::exitApplication,
                )
            }
        }
    }
}