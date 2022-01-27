package com.sshevtsov.stopwatch.domain

import com.sshevtsov.stopwatch.data.StopwatchState

interface StopwatchStateCalculator {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused
}