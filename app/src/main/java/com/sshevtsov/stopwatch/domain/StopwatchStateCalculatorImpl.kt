package com.sshevtsov.stopwatch.domain

import com.sshevtsov.stopwatch.data.StopwatchState

class StopwatchStateCalculatorImpl(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator
) : StopwatchStateCalculator {

    override fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    timestampProvider.getMilliseconds(),
                    oldState.elapsedTime
                )
            }
        }

    override fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Paused -> oldState
            is StopwatchState.Running -> {
                StopwatchState.Paused(
                    elapsedTimeCalculator.calculate(oldState)
                )
            }
        }

}