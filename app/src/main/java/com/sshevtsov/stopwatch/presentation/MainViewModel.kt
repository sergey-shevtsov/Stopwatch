package com.sshevtsov.stopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sshevtsov.stopwatch.data.StopwatchConstants.DEFAULT_TIME
import com.sshevtsov.stopwatch.domain.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder
) : ViewModel() {

    private val mutableTicker = MutableStateFlow(DEFAULT_TIME)

    val ticker: StateFlow<String> get() = mutableTicker.asStateFlow()

    private var job: Job? = null

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        job = viewModelScope.launch {
            while (isActive) {
                mutableTicker.emit(stopwatchStateHolder.getStringTimeRepresentation())
                delay(TICKER_REFRESH_DELAY)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        viewModelScope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIME
    }

    companion object {
        private const val TICKER_REFRESH_DELAY = 40L
    }
}