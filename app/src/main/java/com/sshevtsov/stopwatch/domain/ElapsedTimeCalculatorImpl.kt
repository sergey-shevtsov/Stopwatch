package com.sshevtsov.stopwatch.domain

import com.sshevtsov.stopwatch.data.StopwatchState
import kotlin.math.max

class ElapsedTimeCalculatorImpl(
    private val timestampProvider: TimestampProvider
) : ElapsedTimeCalculator {

    override fun calculate(state: StopwatchState.Running): Long {
        val currentTime = timestampProvider.getMilliseconds()
        val timePassedSinceStart = max(currentTime - state.startTime, 0)
        return timePassedSinceStart + state.elapsedTime
    }
}