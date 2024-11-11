package com.thebrownfoxx.unfocus.screen.clock.state

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration

sealed interface ClockState {
    val headerResource: StringResource
    val duration: Duration
    val runningState: ClockRunningState
    val progress: Float

    val header @Composable get() = stringResource(headerResource)
}

data class TimerInstruction(
    override val headerResource: StringResource,
    override val duration: Duration,
    override val runningState: ClockRunningState = ClockRunningState.Running,
    override val progress: Float = 0f,
) : ClockState

data class Timer(
    override val headerResource: StringResource,
    override val duration: Duration,
    override val runningState: ClockRunningState = ClockRunningState.Running,
    override val progress: Float = 1f,
) : ClockState

data class TimerExpired(
    override val headerResource: StringResource,
    override val duration: Duration = Duration.ZERO,
) : ClockState {
    override val runningState = ClockRunningState.Expired
    override val progress = 0f
}

data class TimerGroup(
    val instruction: TimerInstruction,
    val timer: Timer,
    val expired: TimerExpired,
    val colors: ClockColors,
) {
    fun toList() = listOf(instruction, timer, expired)
}