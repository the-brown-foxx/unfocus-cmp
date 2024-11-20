package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.domain.phase.Phase
import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition

fun PhaseDefinition.toConfigurationSheetState() = ShownConfigurationSheetState(
    phaseQueue = queue.toUiPhaseQueue(),
    focusDuration = Phase.Focus.duration,
    eyeBreakDuration = Phase.EyeBreak.duration,
    sitBreakDuration = Phase.SitBreak.duration,
    fullRestDuration = Phase.FullRest.duration,
    eyeBreaks = cycle.eyeBreaks,
    sitBreaks = cycle.sitBreaks,
    strideDuration = strideDuration,
)