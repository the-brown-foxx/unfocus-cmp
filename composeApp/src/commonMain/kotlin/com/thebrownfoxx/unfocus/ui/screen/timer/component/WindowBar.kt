package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.component.CloseButton
import com.thebrownfoxx.unfocus.ui.component.HoverVisibilityBox
import com.thebrownfoxx.unfocus.ui.component.MinimizeButton
import com.thebrownfoxx.unfocus.ui.component.Spacer
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.app_name

@Composable
fun WindowBar(
    onMinimize: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    hovered: Boolean = interactionSource.collectIsHoveredAsState().value
) {
    Box(modifier = modifier) {
        HoverableIndicator(hovered = hovered)
        HoverVisibilityBox(
            interactionSource = interactionSource,
            hovered = hovered,
        ) {
            ActualWindowBar(onMinimize = onMinimize, onClose = onClose)
        }
    }
}

@Composable
private fun ActualWindowBar(
    onMinimize: () -> Unit,
    onClose: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .alpha(0.6f)
                .padding(4.dp)
                .fillMaxWidth(),
        ) {
            Spacer(width = 16.dp)
            Text(
                text = stringResource(Res.string.app_name),
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.weight(1f))
            MinimizeButton(onClick = onMinimize)
            CloseButton(onClick = onClose)
        }
    }
}

@Composable
private fun BoxScope.HoverableIndicator(hovered: Boolean) {
    val alpha by animateFloatAsState(if (hovered) 0f else 0.4f)

    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .clip(CircleShape)
            .height(8.dp)
            .width(32.dp)
            .background(LocalContentColor.current.copy(alpha = alpha)),
    )
}