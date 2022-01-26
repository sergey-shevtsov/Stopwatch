package com.sshevtsov.stopwatch.presentation.timestamp

import com.sshevtsov.stopwatch.domain.timestamp.TimestampProvider

class TimestampProviderImpl : TimestampProvider {

    override fun getMilliseconds(): Long =
        System.currentTimeMillis()

}