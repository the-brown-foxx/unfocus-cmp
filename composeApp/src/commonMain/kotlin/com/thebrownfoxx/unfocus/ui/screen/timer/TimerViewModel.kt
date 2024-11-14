package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.unfocus.beeper.Beeper
import com.thebrownfoxx.unfocus.beeper.PeriodicBeeper
import com.thebrownfoxx.unfocus.domain.Expired
import com.thebrownfoxx.unfocus.domain.Phase
import com.thebrownfoxx.unfocus.domain.PhaseDurationProvider
import com.thebrownfoxx.unfocus.domain.Timer
import com.thebrownfoxx.unfocus.presence.PresenceAnnouncer
import com.thebrownfoxx.unfocus.presence.PresenceType
import com.thebrownfoxx.unfocus.ui.screen.timer.state.IntroTimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class TimerViewModel(
    private val phaseDurationProvider: PhaseDurationProvider,
    private val presenceAnnouncer: PresenceAnnouncer,
) : ViewModel(), PhaseDurationProvider by phaseDurationProvider {
    private var timer: Timer? = null

    private val _uiState = MutableStateFlow(IntroTimerUiState)
    val uiState = _uiState.asStateFlow()

    private val beeper = Beeper()

    fun onTimerButtonClick() {
        val clockState = _uiState.value
        if (clockState == IntroTimerUiState) {
            timer = Timer().apply {
                collectUiState()
                collectExpiredBeeps()
                collectPresence()
            }
        } else {
            timer?.toggleRunning()
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
                .collect {
                    if (!it.paused) {
                        val presenceType = when (it.phase) {
                            Phase.FullRest -> PresenceType.FullRest
                            else -> PresenceType.Focus
                        }
                        presenceAnnouncer.announcePresence(type = presenceType)
                    } else {
                        presenceAnnouncer.hidePresence()
                    }
                }
        }
    }
}