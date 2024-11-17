package com.thebrownfoxx.unfocus.presence

import kotlinx.datetime.Instant

sealed interface PresenceState

data object PresenceNotAnnounced : PresenceState

data class PresenceAnnounced(
    val type: PresenceType,
    val startTime: Instant,
) : PresenceState

data class PresencePaused(
    val type: PresenceType,
    val startTime: Instant,
    val pauseTime: Instant,
) : PresenceState