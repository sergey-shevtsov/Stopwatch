package com.sshevtsov.stopwatch.di

import com.sshevtsov.stopwatch.domain.*
import com.sshevtsov.stopwatch.presentation.MainViewModel
import com.sshevtsov.stopwatch.presentation.TimestampProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single<TimestampProvider> { TimestampProviderImpl() }

    single<ElapsedTimeCalculator> { ElapsedTimeCalculatorImpl(get()) }

    single<StopwatchStateCalculator> { StopwatchStateCalculatorImpl(get(), get()) }

    viewModel { MainViewModel() }
}