package com.sshevtsov.stopwatch.domain.interactors

import com.sshevtsov.stopwatch.domain.state.StopwatchListState
import kotlinx.coroutines.flow.SharedFlow

interface StopwatchInteractor {

    suspend fun init(): SharedFlow<StopwatchListState>

    suspend fun start(id: String)

    suspend fun pause(id: String)

    suspend fun stop(id: String)

    suspend fun delete(id: String)

    suspend fun add()

}