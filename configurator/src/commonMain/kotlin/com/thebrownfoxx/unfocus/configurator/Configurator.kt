package com.thebrownfoxx.unfocus.configurator

import kotlinx.coroutines.flow.Flow

interface Configurator {
    val configuration: Flow<Configuration>
    suspend fun updateConfiguration(configuration: Configuration)
    suspend fun updateConfiguration(function: (Configuration) -> Configuration)
}