package com.sshevtsov.stopwatch.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sshevtsov.stopwatch.databinding.StopwatchItemBinding
import com.sshevtsov.stopwatch.presentation.model.UIStopwatch

class StopwatchesAdapter(
    private val listener: StopwatchClickListener
) : ListAdapter<UIStopwatch, StopwatchViewHolder>(DiffCallback()) {

    companion object {
        const val ARG_TIME = "arg.time"
        const val ARG_IS_SINGLE = "arg.isSingle"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopwatchViewHolder =
        StopwatchViewHolder(
            StopwatchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: StopwatchViewHolder, position: Int) =
        onBindViewHolder(holder, position, mutableListOf())

    override fun onBindViewHolder(
        holder: StopwatchViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads[0] !is Bundle) {
            holder.bind(listener, getItem(position))
        } else {
            val bundle = payloads[0] as Bundle
            holder.update(bundle)
        }
    }

    override fun getItemCount(): Int =
        currentList.size

}