package com.sshevtsov.stopwatch.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sshevtsov.stopwatch.databinding.ActivityMainBinding
import com.sshevtsov.stopwatch.presentation.adapter.StopwatchClickListener
import com.sshevtsov.stopwatch.presentation.adapter.StopwatchesAdapter
import com.sshevtsov.stopwatch.presentation.model.UIStopwatch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModel()

    private val stopwatchClickListener by lazy {
        object : StopwatchClickListener {

            override fun start(id: String) =
                viewModel.start(id)

            override fun pause(id: String) =
                viewModel.pause(id)

            override fun stop(id: String) =
                viewModel.stop(id)

            override fun delete(id: String) =
                viewModel.delete(id)

        }
    }

    private val stopwatchesAdapter by lazy { StopwatchesAdapter(stopwatchClickListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecycler()
        setupFabListener()
        observeStopwatchesStateUpdates()
    }

    private fun setupRecycler() {
        binding.recyclerStopwatches.adapter = stopwatchesAdapter
    }

    private fun setupFabListener() =
        binding.fabAddStopwatch.setOnClickListener { viewModel.add() }

    private fun observeStopwatchesStateUpdates() =
        lifecycleScope.launchWhenStarted {
            viewModel.stopwatchesState.collect { updateUI(it) }
        }

    private fun updateUI(data: List<UIStopwatch>) =
        stopwatchesAdapter.submitList(data)

}