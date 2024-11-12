package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.unfocus.domain.Timer
import com.thebrownfoxx.unfocus.ui.screen.timer.state.IntroTimerUiState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var timer: Timer? = null

    private val _uiState = MutableStateFlow(IntroTimerUiState)
    val uiState = _uiState.asStateFlow()

    fun onTimerButtonClick() {
        val clockState = _uiState.value
        if (clockState == IntroTimerUiState) {
            val timer = Timer()
            this.timer = timer
            viewModelScope.launch {
                timer.state.collect { timerState ->
                    _uiState.value = timerState.toUiState()
                }
            }
        } else {
            timer?.toggleRunning()
        }
    }
}