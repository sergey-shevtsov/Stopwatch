package com.sshevtsov.stopwatch.domain.state

import com.sshevtsov.stopwatch.domain.model.StopwatchState

sealed class TickerState {

    data class NewState(val state: StopwatchState) : TickerState()

    object Idle : TickerState()

}
