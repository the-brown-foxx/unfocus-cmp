package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.runtime.Composable
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.full_rest_expired_header
import unfocus.composeapp.generated.resources.full_rest_instruction_header
import unfocus.composeapp.generated.resources.full_rest_timer_header
import kotlin.time.Duration.Companion.minutes

val FullRestTimerGroup
    @Composable get() = TimerGroup(
        instruction = FullRestInstruction,
        timer = FullRestTimer,
        expired = FullRestExpired,
        colors = BreakClockColors,
    )

private val fullRestDuration = 20.minutes

val FullRestInstruction = TimerInstruction(
    headerResource = Res.string.full_rest_instruction_header,
    duration = fullRestDuration,
)

val FullRestTimer = Timer(
    headerResource = Res.string.full_rest_timer_header,
    duration = fullRestDuration,
)

val FullRestExpired = TimerExpired(
    headerResource = Res.string.full_rest_expired_header,
)