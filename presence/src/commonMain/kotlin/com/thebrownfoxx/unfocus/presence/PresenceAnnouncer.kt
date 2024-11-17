package com.thebrownfoxx.unfocus.presence

interface PresenceAnnouncer {
    fun announcePresence(type: PresenceType)
    fun pausePresence()
    fun hidePresence()
}