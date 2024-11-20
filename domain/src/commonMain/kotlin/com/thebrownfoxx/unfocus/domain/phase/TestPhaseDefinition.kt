package com.thebrownfoxx.unfocus.domain.phase

import kotlin.time.Duration.Companion.seconds

// Remnant
val TestPhaseDefinition = PhaseDefinition(
    durations = PhaseDurations(
        focusDuration = 5.seconds,
        eyeBreakDuration = 2.seconds,
        sitBreakDuration = 3.seconds,
        fullRestDuration = 10.seconds,
    ),
)