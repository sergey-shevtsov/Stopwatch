package com.sshevtsov.stopwatch.presentation.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.sshevtsov.stopwatch.presentation.adapter.StopwatchesAdapter.Companion.ARG_IS_SINGLE
import com.sshevtsov.stopwatch.presentation.adapter.StopwatchesAdapter.Companion.ARG_TIME
import com.sshevtsov.stopwatch.presentation.model.UIStopwatch

class DiffCallback : DiffUtil.ItemCallback<UIStopwatch>() {

    override fun areItemsTheSame(oldItem: UIStopwatch, newItem: UIStopwatch): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UIStopwatch, newItem: UIStopwatch): Boolean =
        oldItem.isSingle == newItem.isSingle && oldItem.time == newItem.time

    override fun getChangePayload(oldItem: UIStopwatch, newItem: UIStopwatch): Any? {
        if (oldItem.id == newItem.id) {
            val diff = Bundle()

            if (oldItem.isSingle != newItem.isSingle) {
                diff.putBoolean(ARG_IS_SINGLE, newItem.isSingle)
            }
            if (oldItem.time != newItem.time) {
                diff.putString(ARG_TIME, newItem.time)
            }

            return diff
        }

        return super.getChangePayload(oldItem, newItem)
    }
}