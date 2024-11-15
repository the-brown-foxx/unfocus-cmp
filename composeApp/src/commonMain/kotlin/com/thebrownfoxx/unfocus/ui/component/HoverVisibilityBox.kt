package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun HoverVisibilityBox(
    hovered: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val alpha by animateFloatAsState(if (hovered) 1f else 0f)

    Box(
        content = content,
        modifier = modifier
            .alpha(alpha)
            .hoverable(interactionSource),
    )
}