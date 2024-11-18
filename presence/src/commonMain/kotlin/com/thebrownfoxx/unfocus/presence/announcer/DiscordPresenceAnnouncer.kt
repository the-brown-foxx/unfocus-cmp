package com.thebrownfoxx.unfocus.presence.announcer

import com.thebrownfoxx.unfocus.presence.BuildKonfig
import com.thebrownfoxx.unfocus.presence.manager.PresenceAnnounced
import com.thebrownfoxx.unfocus.presence.manager.PresenceNotAnnounced
import com.thebrownfoxx.unfocus.presence.manager.PresencePaused
import com.thebrownfoxx.unfocus.presence.manager.PresenceState
import com.thebrownfoxx.unfocus.presence.manager.PresenceType
import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.data.activity.timestamps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class DiscordPresenceAnnouncer : PresenceAnnouncer {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val ipc = KDiscordIPC(BuildKonfig.DISCORD_CLIENT_ID)

    override fun Flow<PresenceState>.announce() {
        coroutineScope.launch {
            ipc.connect()
        }

        coroutineScope.launch {
            collectIndexed { index, value ->
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
}