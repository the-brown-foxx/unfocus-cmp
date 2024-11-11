package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.runtime.Composable
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.sit_break_expired_header
import unfocus.composeapp.generated.resources.sit_break_instruction_header
import unfocus.composeapp.generated.resources.sit_break_timer_header
import kotlin.time.Duration.Companion.seconds

val SitBreakTimerGroup
    @Composable get() = TimerGroup(
        instruction = SitBreakInstruction,
        timer = SitBreakTimer,
        expired = SitBreakExpired,
        colors = BreakClockColors,
    )

private val sitBreakDuration = 30.seconds

val SitBreakInstruction = TimerInstruction(
    headerResource = Res.string.sit_break_instruction_header,
    duration = sitBreakDuration,
)

val SitBreakTimer = Timer(
    headerResource = Res.string.sit_break_timer_header,
    duration = sitBreakDuration,
)

val SitBreakExpired = TimerExpired(
    headerResource = Res.string.sit_break_expired_header,
)