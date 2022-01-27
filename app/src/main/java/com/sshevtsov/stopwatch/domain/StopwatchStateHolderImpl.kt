package com.sshevtsov.stopwatch.domain

import com.sshevtsov.stopwatch.data.StopwatchState

class StopwatchStateHolderImpl(
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val stopwatchStateCalculator: StopwatchStateCalculator
) : StopwatchStateHolder {

    private var currentState: StopwatchState = StopwatchState.Paused(DEFAULT_ELAPSED_TIME)

    override fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    override fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    override fun stop() {
        currentState = StopwatchState.Paused(DEFAULT_ELAPSED_TIME)
    }

    override fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }

    companion object {
        private const val DEFAULT_ELAPSED_TIME = 0L
    }
}