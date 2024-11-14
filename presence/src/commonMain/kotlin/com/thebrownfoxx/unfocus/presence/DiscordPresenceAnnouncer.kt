package com.thebrownfoxx.unfocus.presence

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.data.activity.timestamps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class DiscordPresenceAnnouncer : PresenceAnnouncer {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val ipc = KDiscordIPC(BuildKonfig.DISCORD_CLIENT_ID)

    init {
        coroutineScope.launch {
            ipc.connect()
        }
    }

    override fun announcePresence(type: PresenceType) {
        val details = when (type) {
            PresenceType.Focus -> "Locked in"
            PresenceType.FullRest -> "Taking a break"
        }

        coroutineScope.launch {
            ipc.activityManager.setActivity(details = details) {
                timestamps(start = Clock.System.now().toEpochMilliseconds())
            }
        }
    }

    override fun hidePresence() {
        coroutineScope.launch {
            ipc.activityManager.clearActivity()
        }
    }
}