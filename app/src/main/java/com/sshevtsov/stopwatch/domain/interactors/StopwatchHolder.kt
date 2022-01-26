package com.sshevtsov.stopwatch.domain.interactors

import com.sshevtsov.stopwatch.domain.model.Stopwatch
import com.sshevtsov.stopwatch.domain.model.StopwatchState
import com.sshevtsov.stopwatch.domain.state.StopwatchStateCalculator
import com.sshevtsov.stopwatch.domain.state.TickerState
import com.sshevtsov.stopwatch.domain.timestamp.TimestampProvider
import com.sshevtsov.stopwatch.domain.util.ElapsedTimeCalculator
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StopwatchHolder(val stopwatch: Stopwatch = Stopwatch()) : KoinComponent {

    private val scope: CoroutineScope by inject()

    private val stopwatchStateCalculator: StopwatchStateCalculator by inject()

    private val elapsedTimeCalculator: ElapsedTimeCalculator by inject()

    private val timestampProvider: TimestampProvider by inject()

    private var job: Job? = null

    private val _ticker: MutableSharedFlow<TickerState> =
        MutableSharedFlow(2, 2, BufferOverflow.DROP_OLDEST)
    val ticker: SharedFlow<TickerState> get() = _ticker.asSharedFlow()

    fun start() {
        if (job == null) startJob()
        stopwatch.state = stopwatchStateCalculator.calculateRunningState(stopwatch.state)
    }

    suspend fun pause() {
        stopwatch.state = stopwatchStateCalculator.calculatePausedState(stopwatch.state)
        stopJob()
        emitChanges()
    }

    suspend fun stop() {
        stopJob()
        clearValue()
        emitChanges()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                if (stopwatch.state is StopwatchState.Running) {
                    (stopwatch.state as StopwatchState.Running).let {
                        it.elapsedTime = getElapsedTime()
                        it.startTime = timestampProvider.getMilliseconds()
                    }
                }
                emitChanges()
                delay(50)
            }
        }
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        stopwatch.state = StopwatchState.Paused()
    }

    private suspend fun emitChanges() {
        _ticker.emit(TickerState.NewState(stopwatch.state))
        _ticker.emit(TickerState.Idle)
    }

    private fun getElapsedTime(): Long {
        val elapsedTime = when (stopwatch.state) {
            is StopwatchState.Paused -> (stopwatch.state as StopwatchState.Paused).elapsedTime
            is StopwatchState.Running -> {
                elapsedTimeCalculator.calculate(stopwatch.state as StopwatchState.Running)
            }
        }

        return elapsedTime
    }
}