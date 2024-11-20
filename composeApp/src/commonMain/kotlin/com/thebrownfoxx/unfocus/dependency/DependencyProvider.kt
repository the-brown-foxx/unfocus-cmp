package com.thebrownfoxx.unfocus.dependency

import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.unfocus.configurator.InMemoryConfigurator
import com.thebrownfoxx.unfocus.presence.announcer.DiscordPresenceAnnouncer
import com.thebrownfoxx.unfocus.presence.manager.AnnouncerPresenceManager

object DependencyProvider {
    val dependencies = object : Dependencies {
        override val presenceManager = AnnouncerPresenceManager(DiscordPresenceAnnouncer())
        override val configurator = InMemoryConfigurator()
    }
}

@Suppress("UnusedReceiverParameter")
val CreationExtras.dependencies get() = DependencyProvider.dependencies