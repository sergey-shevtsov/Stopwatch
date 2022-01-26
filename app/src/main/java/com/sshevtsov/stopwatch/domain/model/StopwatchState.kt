package com.sshevtsov.stopwatch.domain.model

sealed class StopwatchState {

    companion object {
        const val DEFAULT_ELAPSED_TIME = 0L
    }

    data class Paused(
        val elapsedTime: Long = DEFAULT_ELAPSED_TIME
    ) : StopwatchState()

    data class Running(
        var startTime: Long,
        var elapsedTime: Long
    ) : StopwatchState()

}