package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.runtime.Composable

val ClockStateLoop
    @Composable get() = listOf(
        FocusTimerGroup,
        EyeBreakTimerGroup,
        FocusTimerGroup,
        EyeBreakTimerGroup,
        FocusTimerGroup,
        SitBreakTimerGroup,
        FocusTimerGroup,
        EyeBreakTimerGroup,
        FocusTimerGroup,
        EyeBreakTimerGroup,
        FocusTimerGroup,
        FullRestTimerGroup,
    )