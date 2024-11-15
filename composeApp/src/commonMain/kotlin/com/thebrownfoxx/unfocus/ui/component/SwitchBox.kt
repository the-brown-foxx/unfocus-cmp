package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwitchBox(
    checked: Boolean,
    onCheckedToggle: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    content: @Composable () -> Unit,
) {
    val containerColor by animateColorAsState(
        when {
            checked -> color.copy(alpha = 0.3f)
            else -> color.copy(alpha = 0.2f)
        }
    )

    val contentColor by animateColorAsState(
        when {
            checked -> color
            else -> color.copy(alpha = 0.6f)
        }
    )

    Surface(
        color = containerColor,
        modifier = modifier,
        onClick = onCheckedToggle,
        shape = MaterialTheme.shapes.small,
    ) {
        Box(
            modifier = Modifier.size(40.dp),
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                CompositionLocalProvider(LocalContentColor provides contentColor) {
                    content()
                }
            }
            AnimatedVisibility(
                visible = checked,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                SwitchIndicator(contentColor.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
private fun SwitchIndicator(color: Color) {
    val height = 2.dp

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = height, topEnd = height))
            .height(height)
            .width(16.dp)
            .background(color),
    )
}

//@Composable
//fun SwitchIndicator(checked: Boolean) {
//    val checkedProgress by animateFloatAsState(if (checked) 1f else 0f)
//    val contentColor = LocalContentColor.current
//
//    Canvas(
//        modifier = Modifier
//            .size(width = 8.dp, height = 4.dp)
//            .alpha(0.4f),
//    ) {
//        val centerY = size.height / 2
//
//        val strokeWidth = size.height / 2
//        val barStart = Offset(
//            x = 0f + strokeWidth,
//            y = centerY,
//        )
//        val barEnd = Offset(
//            x = size.width - strokeWidth,
//            y = centerY,
//        )
//
//        drawPoints(
//            points = listOf(barStart, barEnd),
//            pointMode = PointMode.Lines,
//            color = contentColor,
//            strokeWidth = strokeWidth,
//            cap = StrokeCap.Round,
//        )
//
//        val circleRadius = size.height / 2
//
//        val circleX = lerp(
//            start = 0f + circleRadius,
//            stop = size.width - circleRadius,
//            fraction = checkedProgress,
//        )
//
//        drawCircle(
//            color = contentColor,
//            radius = circleRadius,
//            center = Offset(x = circleX, y = centerY),
//        )
//    }
//}