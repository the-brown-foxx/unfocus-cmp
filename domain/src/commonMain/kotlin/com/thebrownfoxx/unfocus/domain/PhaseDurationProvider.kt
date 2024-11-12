package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration

interface PhaseDurationProvider {
    val Phase.duration: Duration
}