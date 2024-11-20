package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition

fun getIntroTimerUiState(phaseDefinition: PhaseDefinition) = TimerUiState(
    phaseIndex = 0,
    phaseProgress = 0f,
    phaseQueue = phaseDefinition.queue.toUiPhaseQueue(),
    type = TimerType.Intro,
    header = TimerHeader.Intro,
    fillProgress = 0f,
    duration = with(phaseDefinition) { com.thebrownfoxx.unfocus.domain.phase.Phase.Focus.duration },
    paused = true,
    timerButtonState = TimerButtonState.Run,
    expired = false,
)