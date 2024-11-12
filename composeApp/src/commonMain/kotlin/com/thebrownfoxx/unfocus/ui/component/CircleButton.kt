package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    colors: ButtonColors = buttonColors(),
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = colors.containerColor,
        contentColor = colors.contentColor,
        modifier = modifier,
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            icon()
        }
    }
}

@Composable
fun buttonColors(
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
) = ButtonColors(
    containerColor = containerColor,
    contentColor = contentColor,
)

data class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
)
