package com.thebrownfoxx.unfocus.dependency

import com.thebrownfoxx.unfocus.configurator.Configurator
import com.thebrownfoxx.unfocus.presence.manager.PresenceManager

interface Dependencies {
    val presenceManager: PresenceManager
    val configurator: Configurator
}