package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WindowDraggableArea(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
)