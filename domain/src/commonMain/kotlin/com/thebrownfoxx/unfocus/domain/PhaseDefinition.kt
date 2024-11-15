package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration

interface PhaseDefinition {
    val Phase.duration: Duration
    val queue: List<Phase>
}