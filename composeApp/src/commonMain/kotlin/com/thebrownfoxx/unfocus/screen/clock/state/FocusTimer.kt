package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.focus_expired_header
import unfocus.composeapp.generated.resources.focus_instruction_header
import unfocus.composeapp.generated.resources.focus_timer_header
import unfocus.composeapp.generated.resources.intro_header
import kotlin.time.Duration.Companion.minutes

val FocusTimerGroup
    @Composable get() = TimerGroup(
        instruction = FocusInstruction,
        timer = FocusTimer,
        expired = FocusExpired,
        colors = ClockColors(
            backgroundColor = MaterialTheme.colorScheme.surface,
            fillColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            expiredBackgroundColor = MaterialTheme.colorScheme.primary,
            expiredContentColor = MaterialTheme.colorScheme.onPrimary,
            clockButtonContainerColor = MaterialTheme.colorScheme.primary,
        )
    )

private val focusDuration = 20.minutes

data object Intro : ClockState {
    override val headerResource = Res.string.intro_header
    override val duration = focusDuration
    override val runningState = ClockRunningState.Paused
    override val progress = 0f
}

val FocusInstruction = TimerInstruction(
    headerResource = Res.string.focus_instruction_header,
    duration = focusDuration,
)

val FocusTimer = Timer(
    headerResource = Res.string.focus_timer_header,
    duration = focusDuration,
)

val FocusExpired = TimerExpired(
    headerResource = Res.string.focus_expired_header,
)