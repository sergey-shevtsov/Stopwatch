package com.sshevtsov.stopwatch.di

import com.sshevtsov.stopwatch.domain.calculator.ElapsedTimeCalculator
import com.sshevtsov.stopwatch.domain.calculator.ElapsedTimeCalculatorImpl
import com.sshevtsov.stopwatch.domain.state.StopwatchStateCalculator
import com.sshevtsov.stopwatch.domain.state.StopwatchStateCalculatorImpl
import com.sshevtsov.stopwatch.domain.state.StopwatchStateHolder
import com.sshevtsov.stopwatch.domain.state.StopwatchStateHolderImpl
import com.sshevtsov.stopwatch.domain.timestamp.TimestampMillisecondsFormatter
import com.sshevtsov.stopwatch.domain.timestamp.TimestampProvider
import com.sshevtsov.stopwatch.presentation.main.MainViewModel
import com.sshevtsov.stopwatch.presentation.timestamp.TimestampProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single<TimestampProvider> { TimestampProviderImpl() }

    single { TimestampMillisecondsFormatter() }

    single<ElapsedTimeCalculator> { ElapsedTimeCalculatorImpl(get()) }

    single<StopwatchStateCalculator> { StopwatchStateCalculatorImpl(get(), get()) }

    factory<StopwatchStateHolder> { StopwatchStateHolderImpl(get(), get(), get()) }

    viewModel { MainViewModel(get()) }
}