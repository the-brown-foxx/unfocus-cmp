package com.thebrownfoxx.unfocus.configurator

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class Configuration(
    val focusDuration: Duration,
    val eyeBreakDuration: Duration,
    val sitBreakDuration: Duration,
    val fullRestDuration: Duration,
    val eyeBreaks: Int,
    val sitBreaks: Int,
    val announcePresence: Boolean,
) {
    companion object {
        val Default = Configuration(
            focusDuration = 20.minutes,
            eyeBreakDuration = 20.seconds,
            sitBreakDuration = 1.minutes,
            fullRestDuration = 30.minutes,
            eyeBreaks = 2,
            sitBreaks = 1,
            announcePresence = false,
        )
    }
}