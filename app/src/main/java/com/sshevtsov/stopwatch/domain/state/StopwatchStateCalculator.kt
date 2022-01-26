package com.sshevtsov.stopwatch.domain.state

import com.sshevtsov.stopwatch.domain.util.ElapsedTimeCalculator
import com.sshevtsov.stopwatch.domain.model.StopwatchState
import com.sshevtsov.stopwatch.domain.timestamp.TimestampProvider

class StopwatchStateCalculator(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    timestampProvider.getMilliseconds(),
                    oldState.elapsedTime
                )
            }
            is StopwatchState.Running -> oldState
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                StopwatchState.Paused(
                    elapsedTimeCalculator.calculate(oldState)
                )
            }
            is StopwatchState.Paused -> oldState
        }
}