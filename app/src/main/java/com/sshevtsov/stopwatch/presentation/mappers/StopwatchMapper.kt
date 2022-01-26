package com.sshevtsov.stopwatch.presentation.mappers

import com.sshevtsov.stopwatch.domain.model.Stopwatch
import com.sshevtsov.stopwatch.domain.model.StopwatchState
import com.sshevtsov.stopwatch.domain.timestamp.TimestampMillisecondsFormatter
import com.sshevtsov.stopwatch.presentation.model.UIStopwatch

class StopwatchMapper(
    private val formatter: TimestampMillisecondsFormatter
) {

    private fun toUI(stopwatch: Stopwatch): UIStopwatch =
        UIStopwatch(
            stopwatch.id,
            formatter.format(
                when (stopwatch.state) {
                    is StopwatchState.Paused -> (stopwatch.state as StopwatchState.Paused).elapsedTime
                    is StopwatchState.Running -> (stopwatch.state as StopwatchState.Running).elapsedTime
                }
            ),
            false
        )

    fun toUI(stopwatches: List<Stopwatch>): List<UIStopwatch> =
        stopwatches.map { toUI(it) }

}