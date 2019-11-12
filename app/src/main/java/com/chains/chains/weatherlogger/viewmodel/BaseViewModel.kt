package com.chains.chains.weatherlogger.viewmodel

import androidx.lifecycle.ViewModel
import com.chains.chains.weatherlogger.di.DaggerViewModelInjector
import com.chains.chains.weatherlogger.di.DatabaseModule
import com.chains.chains.weatherlogger.di.NetworkModule
import com.chains.chains.weatherlogger.di.ViewModelInjector


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .databaseModule(DatabaseModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
        }
    }
}