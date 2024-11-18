package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowScope

@Composable
actual fun WindowDraggableArea(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val windowScope = LocalWindowScope.current

    when (windowScope) {
        null -> Box(modifier = modifier) { content() }
        else -> windowScope.WindowDraggableArea(modifier = modifier, content = content)
    }
}

val LocalWindowScope = compositionLocalOf<WindowScope?> { null }