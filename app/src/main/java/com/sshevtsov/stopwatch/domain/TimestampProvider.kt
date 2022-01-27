package com.sshevtsov.stopwatch.domain

interface TimestampProvider {

    fun getMilliseconds(): Long
}