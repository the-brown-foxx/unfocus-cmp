package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.configurator.Configuration
import com.thebrownfoxx.unfocus.domain.phase.PhaseCycle

fun Configuration.toSheetState() = ShownConfigurationSheetState(
    phaseQueue = PhaseCycle(eyeBreaks, sitBreaks).queue.toUiPhaseQueue(),
    focusDuration = focusDuration,
    eyeBreakDuration = eyeBreakDuration,
    sitBreakDuration = sitBreakDuration,
    fullRestDuration = fullRestDuration,
    eyeBreaks = eyeBreaks,
    sitBreaks = sitBreaks,
)