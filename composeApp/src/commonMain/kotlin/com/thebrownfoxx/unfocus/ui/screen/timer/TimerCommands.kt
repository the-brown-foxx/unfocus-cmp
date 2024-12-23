package com.thebrownfoxx.unfocus.ui.screen.timer

import com.thebrownfoxx.unfocus.command.Command
import com.thebrownfoxx.unfocus.domain.phase.PhaseCycle
import com.thebrownfoxx.unfocus.domain.phase.PhaseDurations
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

val TimerViewModel.commands
    get() = listOf(
        Command(keyword = "presence") { toggleAnnouncePresence() },
        Command(keyword = "durations", argumentCount = 8) { arguments ->
            val durations = arguments.minutesSecondsToDurations()
            setPhaseDurations(durations)
        },
        Command(keyword = "durations", argumentCount = 4) { arguments ->
            val durations = arguments.secondsToDurations()
            setPhaseDurations(durations)
        },
        Command(keyword = "cycle", argumentCount = 2) { arguments ->
            val cycle = arguments.cycle()
            setPhaseCycle(cycle)
        },
        Command(keyword = "configure", argumentCount = 10) { arguments ->
            val cycle = arguments.cycle()
            val durations = arguments.subList(2, 10).minutesSecondsToDurations()
            setPhaseCycle(cycle)
            setPhaseDurations(durations)
        },
        Command(keyword = "configure", argumentCount = 6) { arguments ->
            val cycle = arguments.cycle()
            val durations = arguments.subList(2, 6).secondsToDurations()
            setPhaseCycle(cycle)
            setPhaseDurations(durations)
        },
        Command(keyword = "skip") { skipPhase() },
        Command(keyword = "test") { enableTestMode() },
        Command(keyword = "reset") { reset() },
    )

private fun List<String>.minutesSecondsToDurations() = PhaseDurations(
    focusDuration = getOrNull(0).toMinutes() + getOrNull(1).toSeconds(),
    eyeBreakDuration = getOrNull(2).toMinutes() + getOrNull(3).toSeconds(),
    sitBreakDuration = getOrNull(4).toMinutes() + getOrNull(5).toSeconds(),
    fullRestDuration = getOrNull(6).toMinutes() + getOrNull(7).toSeconds(),
)

private fun List<String>.secondsToDurations() = PhaseDurations(
    focusDuration = getOrNull(0).toSeconds(),
    eyeBreakDuration = getOrNull(1).toSeconds(),
    sitBreakDuration = getOrNull(2).toSeconds(),
    fullRestDuration = getOrNull(3).toSeconds(),
)

private fun List<String>.cycle() = PhaseCycle(
    eyeBreaks = getOrNull(0)?.toInt() ?: 0,
    sitBreaks = getOrNull(1)?.toInt() ?: 0,
)

private fun String?.toMinutes() = this?.toLongOrNull()?.minutes ?: Duration.ZERO

private fun String?.toSeconds() = this?.toIntOrNull()?.seconds ?: Duration.ZERO