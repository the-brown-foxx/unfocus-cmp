package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.unfocus.beeper.Beeper
import com.thebrownfoxx.unfocus.beeper.PeriodicBeeper
import com.thebrownfoxx.unfocus.configurator.Configurator
import com.thebrownfoxx.unfocus.domain.phase.DefaultPhaseDefinition
import com.thebrownfoxx.unfocus.domain.phase.Phase
import com.thebrownfoxx.unfocus.domain.phase.PhaseCycle
import com.thebrownfoxx.unfocus.domain.phase.PhaseDefinition
import com.thebrownfoxx.unfocus.domain.phase.PhaseDurations
import com.thebrownfoxx.unfocus.domain.phase.TestPhaseDefinition
import com.thebrownfoxx.unfocus.domain.timer.Expired
import com.thebrownfoxx.unfocus.domain.timer.Timer
import com.thebrownfoxx.unfocus.domain.timer.TimerState
import com.thebrownfoxx.unfocus.presence.manager.PresenceManager
import com.thebrownfoxx.unfocus.presence.manager.PresenceType
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.HiddenConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.LockedConfigurationSheetField
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ShownConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.getIntroTimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toPhaseDefinition
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiPhaseQueue
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModel(
    private val presenceManager: PresenceManager,
    private val configurator: Configurator,
) : ViewModel() {
    private val beeper = Beeper()

    private val inIntro = MutableStateFlow(true)

    private val phaseDefinition = configurator.configuration
        .mapLatest { it.toPhaseDefinition() }
        .stateInHere(DefaultPhaseDefinition)

    private val timer = combine(inIntro, phaseDefinition) { inIntro, phaseDefinition ->
        if (inIntro) null
        else Timer(phaseDefinition)
    }.stateInHere(null)

    val flashTaskbar = timer.flatMapLatest { timer ->
        timer?.state
            ?.distinctUntilChangedBy { it.values is Expired }
            ?.filter { it.values is Expired }
            ?: flowOf()
    }

    val uiState = combine(timer, phaseDefinition) { timer, phaseDefinition ->
        timer to phaseDefinition
    }.flatMapLatest { (timer, phaseDefinition) ->
        timer?.state?.map { timerState -> phaseDefinition.toUiState(timerState) }
            ?: flowOf(getIntroTimerUiState(phaseDefinition))
    }.stateInHere(getIntroTimerUiState(DefaultPhaseDefinition))

    val announcePresence = configurator.configuration.mapLatest { it.announcePresence }
        .stateInHere(false)

    private val _configurationSheetState =
        MutableStateFlow<ConfigurationSheetState>(HiddenConfigurationSheetState)
    val configurationSheetState = _configurationSheetState.asStateFlow()

    init {
        timer.runningFold<Timer?, Timer?>(null) { oldTimer, newTimer ->
            oldTimer?.cancel()
            newTimer
        }

        val beepInterval = 1.minutes
        var periodicBeeper: PeriodicBeeper? = null
        viewModelScope.launch {
            timer.collectLatest { timer ->
                timer?.state
                    ?.distinctUntilChangedBy { it.values is Expired }
                    ?.collect {
                        periodicBeeper?.cancel()
                        if (it.values is Expired) periodicBeeper = beeper.beepEvery(beepInterval)
                    }
            }
        }

        viewModelScope.launch {
            timer.collectLatest { timer ->
                if (timer != null) {
                    combine(timer.state, announcePresence) { state, announcePresence ->
                        state to announcePresence
                    }.distinctUntilChangedBy { (it, announcePresence) ->
                        (it.phase == Phase.FullRest) to it.values.paused to announcePresence
                    }.collect { (state, announcePresence) ->
                        if (announcePresence) state.setPresence()
                    }
                }
            }
        }
    }

    fun onTimerButtonClick() {
        val timer = timer.value
        when {
            timer != null -> timer.toggleRunning()
            else -> inIntro.value = false
        }
    }

    fun toggleAnnouncePresence() {
        viewModelScope.launch {
            configurator.updateConfiguration { oldConfiguration ->
                val announcePresence = !oldConfiguration.announcePresence
                if (!announcePresence) presenceManager.hidePresence()
                oldConfiguration.copy(announcePresence = announcePresence)
            }
        }
    }

    fun skipPhase() {
        val timer = timer.value
        when {
            timer != null -> timer.skipPhase()
            else -> inIntro.value = false
        }
    }

    fun enableTestMode() {
        setPhaseDefinition(TestPhaseDefinition)
    }

    fun setPhaseDurations(phaseDurations: PhaseDurations) {
        val phaseDefinition = phaseDefinition.value
        setPhaseDefinition(PhaseDefinition(phaseDurations, phaseDefinition.cycle))
    }

    fun setPhaseCycle(phaseCycle: PhaseCycle) {
        val phaseDefinition = phaseDefinition.value
        setPhaseDefinition(PhaseDefinition(phaseDefinition.durations, phaseCycle))
    }

    fun reset() {
        setPhaseDefinition(DefaultPhaseDefinition)
    }

    fun showConfigurationSheet() {
        viewModelScope.launch {
            _configurationSheetState.value =
                configurator.configuration.first().toPhaseDefinition().toConfigurationSheetState()
        }
    }

    fun hideConfigurationSheet() {
        _configurationSheetState.value = HiddenConfigurationSheetState
    }

    fun updateEyeBreaks(eyeBreaks: Int) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            with(configurationSheetState) {
                val phaseDefinition = PhaseDefinition(
                    lockedField = lockedField,
                    focusDuration = focusDuration,
                    eyeBreakDuration = eyeBreakDuration,
                    sitBreakDuration = sitBreakDuration,
                    fullRestDuration = fullRestDuration,
                    strideDuration = strideDuration,
                    eyeBreaks = eyeBreaks,
                    sitBreaks = sitBreaks,
                )

                _configurationSheetState.value = copy(
                    eyeBreaks = eyeBreaks,
                    focusDuration = phaseDefinition.durations.focusDuration,
                    strideDuration = phaseDefinition.strideDuration,
                    phaseQueue = phaseDefinition.queue.toUiPhaseQueue(),
                )
            }
        }
    }

    fun updateSitBreaks(sitBreaks: Int) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            with(configurationSheetState) {
                val phaseDefinition = PhaseDefinition(
                    lockedField = lockedField,
                    focusDuration = focusDuration,
                    eyeBreakDuration = eyeBreakDuration,
                    sitBreakDuration = sitBreakDuration,
                    fullRestDuration = fullRestDuration,
                    strideDuration = strideDuration,
                    eyeBreaks = eyeBreaks,
                    sitBreaks = sitBreaks,
                )

                _configurationSheetState.value = copy(
                    sitBreaks = sitBreaks,
                    focusDuration = phaseDefinition.durations.focusDuration,
                    strideDuration = phaseDefinition.strideDuration,
                    phaseQueue = phaseDefinition.queue.toUiPhaseQueue(),
                )
            }
        }
    }

    fun updateFocusDuration(focusDuration: Duration) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            with(configurationSheetState) {
                val phaseDefinition = PhaseDefinition(
                    lockedField = lockedField,
                    focusDuration = focusDuration,
                    eyeBreakDuration = eyeBreakDuration,
                    sitBreakDuration = sitBreakDuration,
                    fullRestDuration = fullRestDuration,
                    strideDuration = strideDuration,
                    eyeBreaks = eyeBreaks,
                    sitBreaks = sitBreaks,
                )

                _configurationSheetState.value = copy(
                    focusDuration = phaseDefinition.durations.focusDuration,
                    strideDuration = phaseDefinition.strideDuration,
                    lockedField = LockedConfigurationSheetField.FocusDuration,
                )
            }
        }
    }

    fun updateEyeBreakDuration(eyeBreakDuration: Duration) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            with(configurationSheetState) {
                val phaseDefinition = PhaseDefinition(
                    lockedField = lockedField,
                    focusDuration = focusDuration,
                    eyeBreakDuration = eyeBreakDuration,
                    sitBreakDuration = sitBreakDuration,
                    fullRestDuration = fullRestDuration,
                    strideDuration = strideDuration,
                    eyeBreaks = eyeBreaks,
                    sitBreaks = sitBreaks,
                )

                _configurationSheetState.value = copy(
                    eyeBreakDuration = phaseDefinition.durations.eyeBreakDuration,
                    focusDuration = phaseDefinition.durations.focusDuration,
                    strideDuration = phaseDefinition.strideDuration,
                    lockedField = LockedConfigurationSheetField.StrideDuration,
                )
            }
        }
    }

    fun updateSitBreakDuration(sitBreakDuration: Duration) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            with(configurationSheetState) {
                val phaseDefinition = PhaseDefinition(
                    lockedField = lockedField,
                    focusDuration = focusDuration,
                    eyeBreakDuration = eyeBreakDuration,
                    sitBreakDuration = sitBreakDuration,
                    fullRestDuration = fullRestDuration,
                    strideDuration = strideDuration,
                    eyeBreaks = eyeBreaks,
                    sitBreaks = sitBreaks,
                )

                _configurationSheetState.value = copy(
                    sitBreakDuration = phaseDefinition.durations.sitBreakDuration,
                    focusDuration = phaseDefinition.durations.focusDuration,
                    strideDuration = phaseDefinition.strideDuration,
                )
            }
        }
    }

    fun updateFullRestDuration(fullRestDuration: Duration) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            _configurationSheetState.value =
                configurationSheetState.copy(fullRestDuration = fullRestDuration)
        }
    }

    fun updateStrideDuration(strideDuration: Duration) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            with(configurationSheetState) {
                val phaseDefinition = PhaseDefinition(
                    strideDuration = strideDuration,
                    eyeBreakDuration = eyeBreakDuration,
                    sitBreakDuration = sitBreakDuration,
                    fullRestDuration = fullRestDuration,
                    eyeBreaks = eyeBreaks,
                    sitBreaks = sitBreaks,
                )

                _configurationSheetState.value = copy(
                    strideDuration = strideDuration,
                    focusDuration = phaseDefinition.durations.focusDuration,
                    lockedField = LockedConfigurationSheetField.StrideDuration,
                )
            }
        }
    }

    fun updateLockedConfigurationSheetField(
        lockedConfigurationSheetField: LockedConfigurationSheetField,
    ) {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            _configurationSheetState.value =
                configurationSheetState.copy(lockedField = lockedConfigurationSheetField)
        }
    }

    fun saveConfiguration() {
        val configurationSheetState = _configurationSheetState.value
        if (configurationSheetState is ShownConfigurationSheetState) {
            viewModelScope.launch {
                configurator.updateConfiguration { oldConfiguration ->
                    with(configurationSheetState) {
                        oldConfiguration.copy(
                            focusDuration = focusDuration,
                            eyeBreakDuration = eyeBreakDuration,
                            sitBreakDuration = sitBreakDuration,
                            fullRestDuration = fullRestDuration,
                            eyeBreaks = eyeBreaks,
                            sitBreaks = sitBreaks,
                        )
                    }
                }
            }
            hideConfigurationSheet()
        }
    }

    private fun TimerState.setPresence() {
        if (!this.values.paused) {
            val presenceType = when (phase) {
                Phase.FullRest -> PresenceType.FullRest
                else -> PresenceType.Focus
            }
            presenceManager.announcePresence(type = presenceType)
        } else {
            presenceManager.pausePresence()
        }
    }

    private fun PhaseDefinition(
        lockedField: LockedConfigurationSheetField,
        focusDuration: Duration,
        eyeBreakDuration: Duration,
        sitBreakDuration: Duration,
        fullRestDuration: Duration,
        strideDuration: Duration,
        eyeBreaks: Int,
        sitBreaks: Int,
    ) = when (lockedField) {
        LockedConfigurationSheetField.FocusDuration -> PhaseDefinition(
            durations = PhaseDurations(
                focusDuration = focusDuration,
                eyeBreakDuration = eyeBreakDuration,
                sitBreakDuration = sitBreakDuration,
                fullRestDuration = fullRestDuration,
            ),
            cycle = PhaseCycle(
                eyeBreaks = eyeBreaks,
                sitBreaks = sitBreaks,
            ),
        )

        LockedConfigurationSheetField.StrideDuration -> PhaseDefinition(
            strideDuration = strideDuration,
            eyeBreakDuration = eyeBreakDuration,
            sitBreakDuration = sitBreakDuration,
            fullRestDuration = fullRestDuration,
            eyeBreaks = eyeBreaks,
            sitBreaks = sitBreaks,
        )
    }

    private fun setPhaseDefinition(phaseDefinition: PhaseDefinition) {
        viewModelScope.launch {
            inIntro.value = true
            configurator.updateConfiguration { oldConfiguration ->
                with(phaseDefinition) {
                    oldConfiguration.copy(
                        focusDuration = Phase.Focus.duration,
                        eyeBreakDuration = Phase.EyeBreak.duration,
                        sitBreakDuration = Phase.SitBreak.duration,
                        fullRestDuration = Phase.FullRest.duration,
                        eyeBreaks = cycle.eyeBreaks,
                        sitBreaks = cycle.sitBreaks,
                    )
                }
            }
        }
    }

    private fun <T> Flow<T>.stateInHere(initialValue: T) =
        stateIn(viewModelScope, SharingStarted.Eagerly, initialValue)
}