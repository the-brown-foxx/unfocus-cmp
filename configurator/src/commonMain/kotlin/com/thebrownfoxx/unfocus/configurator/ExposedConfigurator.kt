package com.thebrownfoxx.unfocus.configurator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.duration
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert

class ExposedConfigurator(database: Database) : Configurator {
    private object ConfigurationTable : Table() {
        val id = integer("id").autoIncrement()
        val focusDuration = duration("focus_duration")
        val eyeBreakDuration = duration("eye_break_duration")
        val sitBreakDuration = duration("sit_break_duration")
        val fullRestDuration = duration("full_rest_duration")
        val eyeBreaks = integer("eye_breaks")
        val sitBreaks = integer("sit_breaks")
        val announcePresence = bool("announce_presence")

        override val primaryKey = PrimaryKey(id)
    }

    private val _configuration = MutableStateFlow(Configuration.Default)
    override val configuration: Flow<Configuration> = _configuration.asStateFlow()

    init {
        transaction(database) {
            SchemaUtils.create(ConfigurationTable)
            _configuration.value = getLatestConfiguration()
        }
    }

    override suspend fun updateConfiguration(configuration: Configuration) {
        val newConfiguration = dbQuery {
            ConfigurationTable.upsert {
                it[id] = 0
                it[focusDuration] = configuration.focusDuration
                it[eyeBreakDuration] = configuration.eyeBreakDuration
                it[sitBreakDuration] = configuration.sitBreakDuration
                it[fullRestDuration] = configuration.fullRestDuration
                it[eyeBreaks] = configuration.eyeBreaks
                it[sitBreaks] = configuration.sitBreaks
                it[announcePresence] = configuration.announcePresence
            }

            getLatestConfiguration()
        }

        _configuration.emit(newConfiguration)
    }

    override suspend fun updateConfiguration(function: (Configuration) -> Configuration) {
        val oldConfiguration = dbQuery { getLatestConfiguration() }
        updateConfiguration(function(oldConfiguration))
    }

    private fun getLatestConfiguration() = ConfigurationTable
        .selectAll()
        .orderBy(ConfigurationTable.id, order = SortOrder.DESC)
        .limit(1)
        .firstOrNull()
        ?.toConfiguration()
        ?: Configuration.Default

    private fun ResultRow.toConfiguration() = Configuration(
        focusDuration = this[ConfigurationTable.focusDuration],
        eyeBreakDuration = this[ConfigurationTable.eyeBreakDuration],
        sitBreakDuration = this[ConfigurationTable.sitBreakDuration],
        fullRestDuration = this[ConfigurationTable.fullRestDuration],
        eyeBreaks = this[ConfigurationTable.eyeBreaks],
        sitBreaks = this[ConfigurationTable.sitBreaks],
        announcePresence = this[ConfigurationTable.announcePresence],
    )

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}