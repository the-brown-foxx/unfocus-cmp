package com.thebrownfoxx.unfocus.ui.screen.timer

import com.thebrownfoxx.unfocus.command.Command
import com.thebrownfoxx.unfocus.domain.UserPhaseDurations
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

val TimerViewModel.commands
    get() = listOf(
        Command(keyword = "presence", argumentCount = 1) { arguments ->
            val announce = arguments.getOrNull(0) == "true"
            setAnnouncePresence(announce)
        },
        Command(keyword = "presence") { toggleAnnouncePresence() },
        Command(keyword = "durations", argumentCount = 8) { arguments ->
            val durations = arguments.minutesSecondsToDuration()
            setUserPhaseDurations(durations)
        },
        Command(keyword = "durations", argumentCount = 4) { arguments ->
            val durations = arguments.secondsToDuration()
            setUserPhaseDurations(durations)
        },
        Command(keyword = "skip") { skipPhase() },
        Command(keyword = "test") { enableTestMode() },
        Command(keyword = "reset") { reset() },
    )

private fun List<String>.minutesSecondsToDuration() = UserPhaseDurations(
    focusDuration = getOrNull(0).toMinutes() + getOrNull(1).toSeconds(),
    eyeBreakDuration = getOrNull(2).toMinutes() + getOrNull(3).toSeconds(),
    sitBreakDuration = getOrNull(4).toMinutes() + getOrNull(5).toSeconds(),
    fullRestDuration = getOrNull(6).toMinutes() + getOrNull(7).toSeconds(),
)

private fun List<String>.secondsToDuration() = UserPhaseDurations(
    focusDuration = getOrNull(0).toSeconds(),
    eyeBreakDuration = getOrNull(1).toSeconds(),
    sitBreakDuration = getOrNull(2).toSeconds(),
    fullRestDuration = getOrNull(3).toSeconds(),
)

private fun String?.toMinutes() = this?.toLongOrNull()?.minutes ?: Duration.ZERO

private fun String?.toSeconds() = this?.toIntOrNull()?.seconds ?: Duration.ZERO