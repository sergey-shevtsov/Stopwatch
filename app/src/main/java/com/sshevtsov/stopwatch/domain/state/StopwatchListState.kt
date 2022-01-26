package com.sshevtsov.stopwatch.domain.state

import com.sshevtsov.stopwatch.domain.model.Stopwatch

sealed class StopwatchListState {

    data class NewData(val data: List<Stopwatch>) : StopwatchListState()

    object Idle : StopwatchListState()

}