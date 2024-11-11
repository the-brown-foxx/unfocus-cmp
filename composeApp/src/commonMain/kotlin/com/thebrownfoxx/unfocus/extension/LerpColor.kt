package com.thebrownfoxx.unfocus.extension

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp

fun lerp(
    start: Color,
    stop: Color,
    fraction: Float,
) = Color(
    red = lerp(
        start = start.red,
        stop = stop.red,
        fraction,
    ),
    green = lerp(
        start = start.green,
        stop = stop.green,
        fraction,
    ),
    blue = lerp(
        start = start.blue,
        stop = stop.blue,
        fraction,
    ),
)