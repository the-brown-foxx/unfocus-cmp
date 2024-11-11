package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.runtime.Composable
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.eye_break_expired_header
import unfocus.composeapp.generated.resources.eye_break_instruction_header
import unfocus.composeapp.generated.resources.eye_break_timer_header
import kotlin.time.Duration.Companion.seconds

private val eyeBreakDuration = 20.seconds

val EyeBreakInstruction = TimerInstruction(
    headerResource = Res.string.eye_break_instruction_header,
    duration = eyeBreakDuration,
)

val EyeBreakTimer = Timer(
    headerResource = Res.string.eye_break_timer_header,
    duration = eyeBreakDuration,
)

val EyeBreakExpired = TimerExpired(
    headerResource = Res.string.eye_break_expired_header,
)

val EyeBreakTimerGroup
    @Composable get() = TimerGroup(
        instruction = EyeBreakInstruction,
        timer = EyeBreakTimer,
        expired = EyeBreakExpired,
        colors = BreakClockColors,
    )
