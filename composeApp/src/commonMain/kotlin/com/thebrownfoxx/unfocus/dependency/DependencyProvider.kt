package com.thebrownfoxx.unfocus.dependency

import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.unfocus.configurator.ExposedConfigurator
import com.thebrownfoxx.unfocus.presence.announcer.DiscordPresenceAnnouncer
import com.thebrownfoxx.unfocus.presence.manager.AnnouncerPresenceManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.io.File
import java.sql.Connection

object DependencyProvider {
    val dependencies = object : Dependencies {
        override val presenceManager = AnnouncerPresenceManager(DiscordPresenceAnnouncer())

        private val database = run {
            val localAppData = System.getenv("LOCALAPPDATA")
            val unfocusDirectory = File("$localAppData/Unfocus")
            unfocusDirectory.mkdirs()
            Database.connect(
                url = "jdbc:sqlite:/${unfocusDirectory.path}/config.db",
                driver = "org.sqlite.JDBC",
            ).also {
                TransactionManager.manager.defaultIsolationLevel =
                    Connection.TRANSACTION_SERIALIZABLE
            }
        }

        override val configurator = ExposedConfigurator(database)
    }
}

@Suppress("UnusedReceiverParameter")
val CreationExtras.dependencies get() = DependencyProvider.dependencies