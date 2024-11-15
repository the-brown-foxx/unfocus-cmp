package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.unfocus.beeper.Beeper
import com.thebrownfoxx.unfocus.beeper.PeriodicBeeper
import com.thebrownfoxx.unfocus.domain.DefaultPhaseDefinition
import com.thebrownfoxx.unfocus.domain.Expired
import com.thebrownfoxx.unfocus.domain.Phase
import com.thebrownfoxx.unfocus.domain.PhaseDefinition
import com.thebrownfoxx.unfocus.domain.TestPhaseDefinition
import com.thebrownfoxx.unfocus.domain.Timer
import com.thebrownfoxx.unfocus.domain.TimerState
import com.thebrownfoxx.unfocus.domain.UserPhaseCycle
import com.thebrownfoxx.unfocus.domain.UserPhaseDefinition
import com.thebrownfoxx.unfocus.domain.UserPhaseDurations
import com.thebrownfoxx.unfocus.presence.PresenceAnnouncer
import com.thebrownfoxx.unfocus.presence.PresenceType
import com.thebrownfoxx.unfocus.ui.screen.timer.state.TimerType
import com.thebrownfoxx.unfocus.ui.screen.timer.state.getIntroTimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class TimerViewModel(private val presenceAnnouncer: PresenceAnnouncer) : ViewModel() {
    private var timer: Timer? = null

    private var phaseDefinition: PhaseDefinition = DefaultPhaseDefinition

    private val _uiState = MutableStateFlow(getIntroTimerUiState(phaseDefinition))
    val uiState = _uiState.asStateFlow()

    private var announcePresence = false

    private val beeper = Beeper()

    fun onTimerButtonClick() {
        val clockState = _uiState.value
        if (clockState.type == TimerType.Intro) {
            startTimer()
        } else {
            timer?.toggleRunning()
        }
    }

    private fun startTimer() {
        timer = Timer(phaseDefinition).apply {
            collectUiState()
            collectExpiredBeeps()
            collectPresence()
        }
    }

    private fun Timer.collectUiState() {
        viewModelScope.launch {
            state.collect { timerState ->
                _uiState.value = toUiState(timerState)
            }
        }
    }

    private fun Timer.collectExpiredBeeps() {
        val beepInterval = 1.minutes
        var periodicBeeper: PeriodicBeeper? = null

        viewModelScope.launch {
            state
                .distinctUntilChangedBy { it is Expired }
                .collect {
                    if (it is Expired && periodicBeeper == null) {
                        periodicBeeper = beeper.beepEvery(beepInterval)
                    } else {
                        periodicBeeper?.cancel()
                        periodicBeeper = null
                    }
                }
        }
    }

    private fun Timer.collectPresence() {
        viewModelScope.launch {
            state
                .distinctUntilChangedBy {
                    (it.phase == Phase.FullRest) to it.paused
                }
                .collect { it.setPresence() }
        }
    }

    private fun TimerState?.setPresence() {
        if (this?.paused == false && announcePresence) {
            val presenceType = when (phase) {
                Phase.FullRest -> PresenceType.FullRest
                else -> PresenceType.Focus
            }
            presenceAnnouncer.announcePresence(type = presenceType)
        } else {
            presenceAnnouncer.hidePresence()
        }
    }

    fun setAnnouncePresence(announcePresence: Boolean) {
        if (announcePresence != this.announcePresence) {
            this.announcePresence = announcePresence
            timer?.state?.value.setPresence()
        }
    }

    fun toggleAnnouncePresence() {
        setAnnouncePresence(!announcePresence)
    }

    fun skipPhase() {
        if (_uiState.value.type == TimerType.Intro) {
            startTimer()
        } else {
            timer?.skipPhase()
        }
    }

    fun enableTestMode() {
        setPhaseDefinition(TestPhaseDefinition)
    }

    fun setUserPhaseDurations(userPhaseDurations: UserPhaseDurations) {
        val phaseDefinition = phaseDefinition
        if (phaseDefinition is UserPhaseDefinition) {
            setPhaseDefinition(UserPhaseDefinition(userPhaseDurations, phaseDefinition.cycle))
        } else {
            setPhaseDefinition(UserPhaseDefinition(durations = userPhaseDurations))
        }
    }

    fun setUserPhaseCycle(userPhaseCycle: UserPhaseCycle) {
        val phaseDefinition = phaseDefinition
        if (phaseDefinition is UserPhaseDefinition) {
            setPhaseDefinition(UserPhaseDefinition(phaseDefinition.durations, userPhaseCycle))
        } else {
            setPhaseDefinition(UserPhaseDefinition(cycle = userPhaseCycle))
        }
    }

    fun reset() {
        setPhaseDefinition(DefaultPhaseDefinition)
    }

    private fun setPhaseDefinition(phaseDefinition: PhaseDefinition) {
        this.phaseDefinition = phaseDefinition
        resetToIntro()
    }

    private fun resetToIntro() {
        timer?.cancel()
        timer = null
        _uiState.value = getIntroTimerUiState(phaseDefinition)
    }
}