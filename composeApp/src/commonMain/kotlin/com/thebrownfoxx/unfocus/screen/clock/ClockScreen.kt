package com.thebrownfoxx.unfocus.screen.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.unfocus.screen.clock.state.ClockState

@Composable
fun ClockScreen(
    state: ClockState,
    onRunningToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
//    val fillColor by animateColorAsState(state.colors.fillColor)
//    val contentColor by animateColorAsState(state.colors.contentColor)
//    val clockButtonContainerColor by animateColorAsState(state.colors.clockButtonContainerColor)
//    val clockButtonContentColor by animateColorAsState(contentColorFor(clockButtonContainerColor))
//
//    Box(modifier = modifier) {
//        when (state) {
////            is TimerIntro
//        }
//        TimerDisplay(
//            header = "Lock in?",
//            duration = 20.minutes,
//            runningState = ClockRunningState.Paused,
//            onRunningToggle = onRunningToggle,
//            contentColor = contentColor,
//            buttonColors = buttonColors(
//                containerColor = clockButtonContentColor,
//                contentColor = clockButtonContainerColor,
//            ),
//        )
//    }
}