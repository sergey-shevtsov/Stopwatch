package com.sshevtsov.stopwatch.domain.interactors

import com.sshevtsov.stopwatch.domain.model.Stopwatch
import com.sshevtsov.stopwatch.domain.state.StopwatchListState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StopwatchInteractorImpl : StopwatchInteractor {

    private val stopwatchList = mutableListOf(StopwatchHolder())

    private val _stopwatchesState =
        MutableSharedFlow<StopwatchListState>(2, 2, BufferOverflow.DROP_OLDEST)

    override suspend fun init(): SharedFlow<StopwatchListState> {
        emitNewData()

        return _stopwatchesState.asSharedFlow()
    }

    override suspend fun start(id: String) {
        stopwatchList.getById(id).let { holder ->
            holder.start()
            holder.ticker.collect {
                changeState()
            }
        }
    }

    override suspend fun pause(id: String) {
        stopwatchList.getById(id).pause()
    }

    override suspend fun stop(id: String) {
        stopwatchList.getById(id).stop()
    }

    override suspend fun delete(id: String) {
        changeState {
            stopwatchList.remove(stopwatchList.getById(id))
        }
    }

    override suspend fun add() {
        changeState {
            stopwatchList.add(StopwatchHolder())
        }
    }

    private suspend fun emitIdleState() {
        _stopwatchesState.emit(StopwatchListState.Idle)
    }

    private suspend fun emitNewData() {
        _stopwatchesState.emit(StopwatchListState.NewData(stopwatchList.extract()))
    }

    private suspend fun changeState(block: () -> Unit) {
        block()
        changeState()
    }

    private suspend fun changeState() {
        emitNewData()
        emitIdleState()
    }

    private fun MutableList<StopwatchHolder>.getById(id: String): StopwatchHolder =
        this.first { it.stopwatch.id == id }

    private fun MutableList<StopwatchHolder>.extract(): List<Stopwatch> =
        this.map { it.stopwatch }
}
