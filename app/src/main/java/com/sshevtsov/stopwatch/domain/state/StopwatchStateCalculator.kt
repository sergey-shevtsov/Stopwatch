package com.sshevtsov.stopwatch.domain.state

import com.sshevtsov.stopwatch.data.StopwatchState

interface StopwatchStateCalculator {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused
}