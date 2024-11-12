package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.unfocus.beeper.Beeper
import com.thebrownfoxx.unfocus.beeper.PeriodicBeeper
import com.thebrownfoxx.unfocus.domain.PhaseDurationProvider
import com.thebrownfoxx.unfocus.domain.Timer
import com.thebrownfoxx.unfocus.ui.screen.timer.state.IntroTimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel(
    private val phaseDurationProvider: PhaseDurationProvider,
) : ViewModel(), PhaseDurationProvider by phaseDurationProvider {
    private var timer: Timer? = null

    private val _uiState = MutableStateFlow(IntroTimerUiState)
    val uiState = _uiState.asStateFlow()

    private val beeper = Beeper()
    private val beepInterval = 30.seconds
    private var periodicBeeper: PeriodicBeeper? = null

    init {
        viewModelScope.launch {
            _uiState.collect {
                if (it.expired && periodicBeeper == null) {
                    periodicBeeper = beeper.beepEvery(beepInterval)
                } else {
                    periodicBeeper?.cancel()
                    periodicBeeper = null
                }
            }
        }
    }

    fun onTimerButtonClick() {
        val clockState = _uiState.value
        if (clockState == IntroTimerUiState) {
            val timer = Timer()
            this.timer = timer
            viewModelScope.launch {
                timer.state.collect { timerState ->
                    _uiState.value = toUiState(timerState)
                }
            }
        } else {
            timer?.toggleRunning()
        }
    }
}