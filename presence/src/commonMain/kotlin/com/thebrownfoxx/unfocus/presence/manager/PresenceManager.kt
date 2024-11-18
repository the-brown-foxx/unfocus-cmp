package com.thebrownfoxx.unfocus.presence.manager

interface PresenceManager {
    fun announcePresence(type: PresenceType)
    fun pausePresence()
    fun hidePresence()
}