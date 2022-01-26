package com.sshevtsov.stopwatch.presentation.adapter

interface StopwatchClickListener {

    fun start(id: String)

    fun pause(id: String)

    fun stop(id: String)

    fun delete(id: String)

}