package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.domain.Phase
import com.thebrownfoxx.unfocus.domain.PhaseDurationProvider

fun getIntroTimerUiState(phaseDurationProvider: PhaseDurationProvider) = TimerUiState(
    type = TimerType.Intro,
    header = TimerHeader.Intro,
    fillProgress = 0f,
    duration = with(phaseDurationProvider) { Phase.Focus.duration },
    paused = true,
    timerButtonState = TimerButtonState.Run,
    expired = false,
)