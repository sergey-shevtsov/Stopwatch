package com.sshevtsov.stopwatch.domain.calculator

import com.sshevtsov.stopwatch.data.StopwatchState

interface ElapsedTimeCalculator {

    fun calculate(state: StopwatchState.Running): Long
}