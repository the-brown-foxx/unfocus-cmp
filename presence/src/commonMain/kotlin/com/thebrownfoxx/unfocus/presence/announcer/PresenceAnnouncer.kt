package com.thebrownfoxx.unfocus.presence.announcer

import com.thebrownfoxx.unfocus.presence.manager.PresenceState
import kotlinx.coroutines.flow.Flow

interface PresenceAnnouncer {
    fun Flow<PresenceState>.announce()
}