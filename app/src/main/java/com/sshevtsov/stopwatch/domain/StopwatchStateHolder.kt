package com.sshevtsov.stopwatch.domain

interface StopwatchStateHolder {

    fun start()

    fun pause()

    fun stop()

    fun getStringTimeRepresentation(): String
}