package com.thebrownfoxx.unfocus.ui.extension

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val PaddingValues.startDp @Composable get() = calculateStartPadding(LocalLayoutDirection.current)
val PaddingValues.topDp get() = calculateTopPadding()
val PaddingValues.endDp @Composable get() = calculateEndPadding(LocalLayoutDirection.current)
val PaddingValues.bottomDp get() = calculateBottomPadding()

val PaddingValues.start @Composable get() = startDp.startPadding
val PaddingValues.top @Composable get() = topDp.topPadding
val PaddingValues.end @Composable get() = endDp.endPadding
val PaddingValues.bottom @Composable get() = bottomDp.bottomPadding

val StatusBarPadding @Composable get() = WindowInsets.statusBars.asPaddingValues()
val NavigationBarPadding @Composable get() = WindowInsets.navigationBars.asPaddingValues()
val StatusBarHeight @Composable get() = StatusBarPadding.topDp
val NavigationBarHeight @Composable get() = NavigationBarPadding.bottomDp

val SafeDrawingPadding @Composable get() = WindowInsets.safeDrawing.asPaddingValues()

val PaddingValues.horizontal @Composable get() = PaddingValues(
    start = startDp,
    end = endDp,
)

val PaddingValues.vertical get() = PaddingValues(
    top = topDp,
    bottom = bottomDp,
)

@Composable
operator fun PaddingValues.plus(other: PaddingValues) = PaddingValues(
    start = startDp + other.startDp,
    top = topDp + other.topDp,
    end = endDp + other.endDp,
    bottom = bottomDp + other.bottomDp,
)

@Composable
operator fun PaddingValues.minus(other: PaddingValues) = PaddingValues(
    start = startDp - other.startDp,
    top = topDp - other.topDp,
    end = endDp - other.endDp,
    bottom = bottomDp - other.bottomDp,
)

enum class PaddingSide {
    Start,
    Top,
    End,
    Bottom,
}

val Dp.padding @Composable get() = PaddingValues(all = this)

@Composable
fun Dp.paddingFor(vararg sides: PaddingSide): PaddingValues {
    var paddingValues = 0.dp.padding
    for (side in sides) {
        paddingValues += when (side) {
            PaddingSide.Start -> PaddingValues(start = this)
            PaddingSide.Top -> PaddingValues(top = this)
            PaddingSide.End -> PaddingValues(end = this)
            PaddingSide.Bottom -> PaddingValues(bottom = this)
        }
    }
    return paddingValues
}

val Dp.startPadding @Composable get() = PaddingValues(start = this)
val Dp.topPadding @Composable get() = PaddingValues(top = this)
val Dp.bottomPadding @Composable get() = PaddingValues(bottom = this)
val Dp.endPadding @Composable get() = PaddingValues(end = this)

val Dp.horizontalPadding @Composable get() = PaddingValues(horizontal = this)
val Dp.verticalPadding @Composable get() = PaddingValues(vertical = this)

@Composable
fun Dp.paddingExcept(vararg sides: PaddingSide): PaddingValues {
    var paddingValues = PaddingValues(all = this)
    for (side in sides) {
        paddingValues -= when (side) {
            PaddingSide.Start -> PaddingValues(start = this)
            PaddingSide.Top -> PaddingValues(top = this)
            PaddingSide.End -> PaddingValues(end = this)
            PaddingSide.Bottom -> PaddingValues(bottom = this)
        }
    }
    return paddingValues
}

@Composable
fun PaddingValues.copy(
    start: Dp = this.startDp,
    top: Dp = this.topDp,
    end: Dp = this.endDp,
    bottom: Dp = this.bottomDp,
) = PaddingValues(
    start = start,
    top = top,
    end = end,
    bottom = bottom,
)

@Composable
fun PaddingValues.except(vararg sides: PaddingSide) = copy(
    start = if (PaddingSide.Start in sides) 0.dp else startDp,
    top = if (PaddingSide.Top in sides) 0.dp else topDp,
    end = if (PaddingSide.End in sides) 0.dp else endDp,
    bottom = if (PaddingSide.Bottom in sides) 0.dp else bottomDp,
)