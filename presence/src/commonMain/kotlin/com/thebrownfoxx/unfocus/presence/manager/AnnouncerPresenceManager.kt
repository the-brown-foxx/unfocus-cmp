package com.thebrownfoxx.unfocus.presence.manager

import com.thebrownfoxx.unfocus.presence.announcer.PresenceAnnouncer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.minutes

class AnnouncerPresenceManager(presenceAnnouncer: PresenceAnnouncer) : PresenceManager {
    private var presenceState = MutableStateFlow<PresenceState>(PresenceNotAnnounced)

    init {
        with(presenceAnnouncer) { presenceState.announce() }
    }

    override fun announcePresence(type: PresenceType) {
        presenceState.update { oldState ->
            when (oldState) {
                PresenceNotAnnounced -> PresenceAnnounced(
                    type = type,
                    startTime = Clock.System.now()
                )

                is PresenceAnnounced -> when {
                    oldState.type == type -> oldState
                    else -> PresenceAnnounced(
                        type = type,
                        startTime = Clock.System.now(),
                    )
                }

                is PresencePaused -> when {
                    oldState.type != type -> PresenceAnnounced(
                        type = type,
                        startTime = Clock.System.now(),
                    )

                    else -> {
                        val now = Clock.System.now()
                        val pauseDuration = now - oldState.pauseTime
                        val startTime = when {
                            pauseDuration > 5.minutes -> now
                            else -> oldState.startTime + pauseDuration
                        }
                        PresenceAnnounced(
                            type = type,
                            startTime = startTime,
                        )
                    }
                }
            }
        }
    }

    override fun pausePresence() {
        presenceState.update { oldState ->
            when (oldState) {
                PresenceNotAnnounced, is PresencePaused -> oldState
                is PresenceAnnounced -> PresencePaused(
                    type = oldState.type,
                    startTime = oldState.startTime,
                    pauseTime = Clock.System.now(),
                )
            }
        }
    }

    override fun hidePresence() {
        presenceState.value = PresenceNotAnnounced
    }
}