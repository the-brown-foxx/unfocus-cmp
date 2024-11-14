package com.thebrownfoxx.unfocus.presence

interface PresenceAnnouncer {
    fun announcePresence(type: PresenceType)
    fun hidePresence()
}

enum class PresenceType {
    Focus,
    FullRest,
}