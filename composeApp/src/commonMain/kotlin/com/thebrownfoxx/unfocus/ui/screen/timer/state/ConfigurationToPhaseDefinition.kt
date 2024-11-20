package com.thebrownfoxx.unfocus.ui.screen.timer.state

import com.thebrownfoxx.unfocus.configurator.Configuration
import com.thebrownfoxx.unfocus.domain.phase.PhaseCycle
import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition
import com.thebrownfoxx.unfocus.domain.phase.PhaseDurations

fun Configuration.toPhaseDefinition() = PhaseDefinition(
    durations = PhaseDurations(
        focusDuration = focusDuration,
        eyeBreakDuration = eyeBreakDuration,
        sitBreakDuration = sitBreakDuration,
        fullRestDuration = fullRestDuration,
    ),
    cycle = PhaseCycle(
        eyeBreaks = eyeBreaks,
        sitBreaks = sitBreaks,
    )
)