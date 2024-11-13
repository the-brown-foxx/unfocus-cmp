package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.domain.Expired
import com.thebrownfoxx.unfocus.domain.Instruction
import com.thebrownfoxx.unfocus.domain.MainTimer
import com.thebrownfoxx.unfocus.domain.Phase
import com.thebrownfoxx.unfocus.domain.PhaseDurationProvider
import com.thebrownfoxx.unfocus.domain.TimerState
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

fun PhaseDurationProvider.toUiState(timerState: TimerState): TimerUiState {
    val type = when (timerState.phase) {
        Phase.Focus -> TimerType.Focus
        Phase.EyeBreak -> TimerType.Break
        Phase.SitBreak -> TimerType.Break
        Phase.FullRest -> TimerType.Rest
    }

    return with(timerState) {
        when (this) {
            is Instruction -> TimerUiState(
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