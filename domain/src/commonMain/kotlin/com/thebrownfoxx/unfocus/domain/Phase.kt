package com.thebrownfoxx.unfocus.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

enum class Phase(val duration: Duration) {
    Focus(duration = 20.minutes),
    EyeBreak(duration = 20.seconds),
    SitBreak(duration = 1.minutes),
    FullRest(duration = 30.minutes);
//    Focus(duration = 20.seconds),
//    EyeBreak(duration = 5.seconds),
//    SitBreak(duration = 10.seconds),
//    FullRest(duration = 20.seconds);

    companion object {
        val queue = listOf(
            Focus,
            EyeBreak,
            Focus,
            EyeBreak,
            Focus,
            SitBreak,
            Focus,
            EyeBreak,
            Focus,
            EyeBreak,
            Focus,
            FullRest,
        )
    }
}