package com.thebrownfoxx.unfocus.configurator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryConfigurator : Configurator {
    private val _configuration = MutableStateFlow(Configuration.Default)
    override val configuration = _configuration.asStateFlow()

    override suspend fun updateConfiguration(configuration: Configuration) {
        _configuration.value = configuration
    }

    override suspend fun updateConfiguration(function: (Configuration) -> Configuration) {
        _configuration.update(function)
    }
}