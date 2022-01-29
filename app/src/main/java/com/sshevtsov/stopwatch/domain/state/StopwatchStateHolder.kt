package com.sshevtsov.stopwatch.domain.state

interface StopwatchStateHolder {

    fun start()

    fun pause()

    fun stop()

    fun getStringTimeRepresentation(): String
}