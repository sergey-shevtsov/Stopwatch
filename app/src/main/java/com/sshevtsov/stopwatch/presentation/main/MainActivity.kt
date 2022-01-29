package com.sshevtsov.stopwatch.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sshevtsov.stopwatch.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeTicker()
        setupListeners()
    }

    private fun observeTicker() {
        lifecycleScope.launchWhenStarted {
            viewModel.ticker.collect { updateUI(it) }
        }
    }

    private fun updateUI(text: String) {
        binding.textViewTime.text = text
    }

    private fun setupListeners() {
        binding.buttonStart.setOnClickListener { viewModel.start() }

        binding.buttonPause.setOnClickListener { viewModel.pause() }

        binding.buttonStop.setOnClickListener { viewModel.stop() }
    }
}