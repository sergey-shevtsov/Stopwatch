package com.sshevtsov.stopwatch.domain.model

import java.util.*

data class Stopwatch(
    val id: String = UUID.randomUUID().toString(),
    var state: StopwatchState = StopwatchState.Paused()
)