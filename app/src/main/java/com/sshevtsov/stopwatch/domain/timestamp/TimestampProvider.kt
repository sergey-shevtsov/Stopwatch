package com.sshevtsov.stopwatch.domain.timestamp

interface TimestampProvider {

    fun getMilliseconds(): Long

}