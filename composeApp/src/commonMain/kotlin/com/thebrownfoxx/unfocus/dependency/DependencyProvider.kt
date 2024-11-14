package com.thebrownfoxx.unfocus.dependency

import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.unfocus.presence.DiscordPresenceAnnouncer

object DependencyProvider {
    val dependencies = object : Dependencies {
        override val presenceAnnouncer = DiscordPresenceAnnouncer()
    }
}

@Suppress("UnusedReceiverParameter")
val CreationExtras.dependencies get() = DependencyProvider.dependencies