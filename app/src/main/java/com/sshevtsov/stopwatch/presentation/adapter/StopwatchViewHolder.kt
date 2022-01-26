package com.sshevtsov.stopwatch.presentation.adapter

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.stopwatch.databinding.StopwatchItemBinding
import com.sshevtsov.stopwatch.presentation.adapter.StopwatchesAdapter.Companion.ARG_IS_SINGLE
import com.sshevtsov.stopwatch.presentation.adapter.StopwatchesAdapter.Companion.ARG_TIME
import com.sshevtsov.stopwatch.presentation.main.MainViewModel
import com.sshevtsov.stopwatch.presentation.model.UIStopwatch

class StopwatchViewHolder(private val binding: StopwatchItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: StopwatchClickListener, uiStopwatch: UIStopwatch) {

        binding.textViewTime.text = uiStopwatch.time

        binding.buttonStart.setOnClickListener { listener.start(uiStopwatch.id) }

        binding.buttonPause.setOnClickListener { listener.pause(uiStopwatch.id) }

        binding.buttonStop.setOnClickListener { listener.stop(uiStopwatch.id) }

        binding.buttonDeleteStopwatch.isVisible = !uiStopwatch.isSingle

        binding.buttonDeleteStopwatch.setOnClickListener { listener.delete(uiStopwatch.id) }

    }

    fun update(bundle: Bundle) {
        if (bundle.containsKey(ARG_TIME)) {
            binding.textViewTime.text = bundle.getString(ARG_TIME)
        }
        if (bundle.containsKey(ARG_IS_SINGLE)) {
            binding.buttonDeleteStopwatch.isVisible =
                !bundle.getBoolean(ARG_IS_SINGLE)
        }
    }

}