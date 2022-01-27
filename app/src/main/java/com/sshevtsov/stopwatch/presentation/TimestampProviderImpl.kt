package com.sshevtsov.stopwatch.presentation

import com.sshevtsov.stopwatch.domain.TimestampProvider

class TimestampProviderImpl : TimestampProvider {
    override fun getMilliseconds(): Long =
        System.currentTimeMillis()
}