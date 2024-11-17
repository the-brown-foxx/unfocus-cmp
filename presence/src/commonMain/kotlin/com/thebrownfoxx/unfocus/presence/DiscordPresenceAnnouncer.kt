package com.thebrownfoxx.unfocus.presence

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.data.activity.timestamps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.minutes

class DiscordPresenceAnnouncer : PresenceAnnouncer {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var presenceState = MutableStateFlow<PresenceState>(PresenceNotAnnounced)

    init {
        val ipc = KDiscordIPC(BuildKonfig.DISCORD_CLIENT_ID)

        coroutineScope.launch {
            ipc.connect()
        }
        coroutineScope.launch {
            presenceState.collectIndexed { index, value ->
                // DiscordPresenceAnnouncer always starts out with PresenceNotAnnounced
                // and unless we have touched the presence state, we don't want IPC to do anything
                if (index == 0) return@collectIndexed

                when (value) {
                    PresenceNotAnnounced, is PresencePaused -> ipc.activityManager.clearActivity()

                    is PresenceAnnounced -> {
                        val details = when (value.type) {
                            PresenceType.Focus -> "Locked in"
                            PresenceType.FullRest -> "Taking a break"
                        }
                        ipc.activityManager.setActivity(details = details) {
                            timestamps(start = value.startTime.toEpochMilliseconds())
                        }
                    }
                }
            }
        }
    }

    // TODO: These functions can be refactored out of DiscordPresenceAnnouncer since they aren't specific to Discord
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