package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.domain.phase.Phase
import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition
import com.thebrownfoxx.unfocus.domain.timer.Expired
import com.thebrownfoxx.unfocus.domain.timer.Instruction
import com.thebrownfoxx.unfocus.domain.timer.MainTimer
import com.thebrownfoxx.unfocus.domain.timer.TimerState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.EyeBreakExpired
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.EyeBreakInstruction
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.EyeBreakPaused
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.EyeBreakTimer
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FocusExpired
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FocusInstruction
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FocusPaused
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FocusTimer
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FullRestExpired
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FullRestInstruction
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FullRestPaused
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.FullRestTimer
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.SitBreakExpired
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.SitBreakInstruction
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.SitBreakPaused
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerHeader.SitBreakTimer

fun PhaseDefinition.toUiState(timerState: TimerState): TimerUiState {
    val phaseQueue = timerState.phaseQueue.toUiPhaseQueue()

    val type = when (timerState.phase) {
        Phase.Focus -> PhaseTimerType.Focus
        Phase.EyeBreak -> BreakTimerType.Eye
        Phase.SitBreak -> BreakTimerType.Sit
        Phase.FullRest -> PhaseTimerType.Rest
    }

    return with(timerState) {
        with(values) {
            when (this) {
                is Instruction -> TimerUiState(
                    phaseIndex = phaseIndex,
                    phaseProgress = 0f,
                    phaseQueue = phaseQueue,
                    type = type,
                    header = when (phase) {
                        Phase.Focus -> if (paused) FocusPaused else FocusInstruction
                        Phase.EyeBreak -> if (paused) EyeBreakPaused else EyeBreakInstruction
                        Phase.SitBreak -> if (paused) SitBreakPaused else SitBreakInstruction
                        Phase.FullRest -> if (paused) FullRestPaused else FullRestInstruction
                    },
                    fillProgress = (duration / Instruction.MaxDuration).toFloat(),
                    duration = phase.duration,
                    paused = paused,
                    timerButtonState = when {
                        paused -> TimerButtonState.Run
                        else -> TimerButtonState.Pause
                    },
                    expired = false,
                )

                is MainTimer -> TimerUiState(
                    phaseIndex = phaseIndex,
                    phaseProgress = 1f - (duration / phase.duration).toFloat(),
                    phaseQueue = phaseQueue,
                    type = type,
                    header = when (phase) {
                        Phase.Focus -> if (paused) FocusPaused else FocusTimer
                        Phase.EyeBreak -> if (paused) EyeBreakPaused else EyeBreakTimer
                        Phase.SitBreak -> if (paused) SitBreakPaused else SitBreakTimer
                        Phase.FullRest -> if (paused) FullRestPaused else FullRestTimer
                    },
                    fillProgress = (duration / phase.duration).toFloat(),
                    duration = duration,
                    paused = paused,
                    timerButtonState = when {
                        paused -> TimerButtonState.Run
                        else -> TimerButtonState.Pause
                    },
                    expired = false,
                )

                is Expired -> TimerUiState(
                    phaseIndex = phaseIndex,
                    phaseProgress = 1f,
                    phaseQueue = phaseQueue,
                    type = type,
                    header = when (phase) {
                        Phase.Focus -> FocusExpired
                        Phase.EyeBreak -> EyeBreakExpired
                        Phase.SitBreak -> SitBreakExpired
                        Phase.FullRest -> FullRestExpired
                    },
                    fillProgress = 0f,
                    duration = duration,
                    paused = false,
                    timerButtonState = TimerButtonState.Stop,
                    expired = true,
                )
            }
        }
    }
}

fun List<Phase>.toUiPhaseQueue() = map {
    when (it) {
        Phase.Focus -> PhaseTimerType.Focus
        Phase.EyeBreak -> BreakTimerType.Eye
        Phase.SitBreak -> BreakTimerType.Sit
        Phase.FullRest -> PhaseTimerType.Rest
    }
}