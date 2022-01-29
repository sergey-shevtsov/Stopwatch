package com.sshevtsov.stopwatch.domain.state

import com.sshevtsov.stopwatch.data.StopwatchConstants.DEFAULT_ELAPSED_TIME
import com.sshevtsov.stopwatch.data.StopwatchState
import com.sshevtsov.stopwatch.domain.calculator.ElapsedTimeCalculator
import com.sshevtsov.stopwatch.domain.timestamp.TimestampMillisecondsFormatter

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
}