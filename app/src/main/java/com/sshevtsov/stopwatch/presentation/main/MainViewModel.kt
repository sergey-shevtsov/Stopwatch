package com.sshevtsov.stopwatch.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sshevtsov.stopwatch.domain.state.StopwatchListState
import com.sshevtsov.stopwatch.domain.interactors.StopwatchInteractor
import com.sshevtsov.stopwatch.presentation.mappers.StopwatchMapper
import com.sshevtsov.stopwatch.presentation.model.UIStopwatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val stopwatchInteractor: StopwatchInteractor,
    private val stopwatchMapper: StopwatchMapper
) : ViewModel() {

    private val _stopwatchesState = MutableStateFlow<List<UIStopwatch>>(listOf())
    val stopwatchesState: StateFlow<List<UIStopwatch>> get() = _stopwatchesState.asStateFlow()

    init {
        observeStopwatchesState()
    }

    private fun observeStopwatchesState() {
        viewModelScope.launch {
            stopwatchInteractor.init()
                .map {
                    when (it) {
                        is StopwatchListState.Idle -> listOf()
                        is StopwatchListState.NewData -> it.data
                    }
                }
                .map { stopwatchMapper.toUI((it)) }
                .map { setIsSingleParam(it) }
                .flowOn(Dispatchers.Default)
                .collect {
                    if (it.isNotEmpty()) _stopwatchesState.value = it
                }
        }
    }

    private fun setIsSingleParam(stopwatches: List<UIStopwatch>): List<UIStopwatch> {
        val isSingle = stopwatches.size == 1
        stopwatches.forEach { it.isSingle = isSingle }
        return stopwatches.toList()
    }

    fun start(id: String) {
        viewModelScope.launch {
            stopwatchInteractor.start(id)
        }
    }

    fun pause(id: String) {
        viewModelScope.launch {
            stopwatchInteractor.pause(id)
        }
    }

    fun stop(id: String) {
        viewModelScope.launch {
            stopwatchInteractor.stop(id)
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            stopwatchInteractor.delete(id)
        }
    }

    fun add() {
        viewModelScope.launch {
            stopwatchInteractor.add()
        }
    }

}

