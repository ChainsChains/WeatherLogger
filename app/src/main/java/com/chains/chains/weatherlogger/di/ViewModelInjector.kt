package com.chains.chains.weatherlogger.di

import com.chains.chains.weatherlogger.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class), (DatabaseModule::class)])
interface ViewModelInjector {
    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder
    }
}