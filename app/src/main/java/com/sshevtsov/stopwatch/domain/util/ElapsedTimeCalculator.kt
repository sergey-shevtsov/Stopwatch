package com.sshevtsov.stopwatch.domain.util

import com.sshevtsov.stopwatch.domain.model.StopwatchState
import com.sshevtsov.stopwatch.domain.timestamp.TimestampProvider
import kotlin.math.max

class ElapsedTimeCalculator(
    private val timestampProvider: TimestampProvider
) {

    fun calculate(state: StopwatchState.Running): Long {
        val currentTime = timestampProvider.getMilliseconds()
        val timePassedSinceStart = max(currentTime - state.startTime, 0)
        return timePassedSinceStart + state.elapsedTime
    }
}