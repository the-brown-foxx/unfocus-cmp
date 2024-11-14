package com.thebrownfoxx.unfocus.dependency

import com.thebrownfoxx.unfocus.presence.PresenceAnnouncer

interface Dependencies {
    val presenceAnnouncer: PresenceAnnouncer
}