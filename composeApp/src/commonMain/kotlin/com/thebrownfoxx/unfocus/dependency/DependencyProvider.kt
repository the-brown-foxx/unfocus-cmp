package com.thebrownfoxx.unfocus.dependency

import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.unfocus.BuildKonfig
import com.thebrownfoxx.unfocus.configurator.ExposedConfigurator
import com.thebrownfoxx.unfocus.configurator.InMemoryConfigurator
import com.thebrownfoxx.unfocus.presence.announcer.DiscordPresenceAnnouncer
import com.thebrownfoxx.unfocus.presence.manager.AnnouncerPresenceManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.io.File
import java.sql.Connection

object DependencyProvider {
    val dependencies = object : Dependencies {
        override val presenceManager = AnnouncerPresenceManager(DiscordPresenceAnnouncer())

        override val configurator = when {
            BuildKonfig.DEBUG -> InMemoryConfigurator()
            else -> ExposedConfigurator()
        }
    }

    private fun ExposedConfigurator(): ExposedConfigurator {
        val localAppData = System.getenv("LOCALAPPDATA")
        val unfocusDirectory = File("$localAppData/Unfocus")
        unfocusDirectory.mkdirs()
        val database = Database.connect(
            url = "jdbc:sqlite:/${unfocusDirectory.path}/config.db",
            driver = "org.sqlite.JDBC",
        )
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE

        return ExposedConfigurator(database)
    }
}

@Suppress("UnusedReceiverParameter")
val CreationExtras.dependencies get() = DependencyProvider.dependencies