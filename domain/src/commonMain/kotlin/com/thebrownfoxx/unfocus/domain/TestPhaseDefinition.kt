package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration.Companion.seconds

// Remnant
val TestPhaseDefinition = UserPhaseDefinition(
    durations = UserPhaseDurations(
        focusDuration = 5.seconds,
        eyeBreakDuration = 2.seconds,
        sitBreakDuration = 3.seconds,
        fullRestDuration = 10.seconds,
    ),
)