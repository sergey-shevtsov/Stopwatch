package com.sshevtsov.stopwatch.presentation

import androidx.lifecycle.ViewModel
import com.sshevtsov.stopwatch.data.StopwatchConstants.DEFAULT_TIME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val mutableTicker = MutableStateFlow(DEFAULT_TIME)

    val ticker: StateFlow<String> get() = mutableTicker.asStateFlow()

    fun start() {
        // todo
    }

    fun pause() {
        // todo
    }

    fun stop() {
        // todo
    }
}