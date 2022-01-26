package com.sshevtsov.stopwatch.di

import com.sshevtsov.stopwatch.domain.interactors.StopwatchInteractor
import com.sshevtsov.stopwatch.domain.interactors.StopwatchInteractorImpl
import com.sshevtsov.stopwatch.domain.state.StopwatchStateCalculator
import com.sshevtsov.stopwatch.domain.timestamp.TimestampMillisecondsFormatter
import com.sshevtsov.stopwatch.domain.timestamp.TimestampProvider
import com.sshevtsov.stopwatch.domain.util.ElapsedTimeCalculator
import com.sshevtsov.stopwatch.presentation.main.MainViewModel
import com.sshevtsov.stopwatch.presentation.mappers.StopwatchMapper
import com.sshevtsov.stopwatch.presentation.timestamp.TimestampProviderImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single<StopwatchInteractor> { StopwatchInteractorImpl() }

    factory { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single { StopwatchStateCalculator(get(), get()) }

    single { ElapsedTimeCalculator(get()) }

    single { StopwatchMapper(get()) }

    single { TimestampMillisecondsFormatter() }

    single<TimestampProvider> { TimestampProviderImpl() }

    viewModel { MainViewModel(get(), get()) }

}