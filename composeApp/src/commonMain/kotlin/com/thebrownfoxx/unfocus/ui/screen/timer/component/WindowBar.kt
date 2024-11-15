package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
) {
    val hovered by interactionSource.collectIsHoveredAsState()

    HoverVisibilityBox(
        interactionSource = interactionSource,
        hovered = hovered,
        modifier = modifier,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
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
}