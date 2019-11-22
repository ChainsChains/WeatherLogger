package com.chains.chains.weatherlogger.di

import android.content.Context
import com.chains.chains.weatherlogger.managers.AddressManager
import com.chains.chains.weatherlogger.viewmodel.ConditionViewModel
import com.chains.chains.weatherlogger.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton
import dagger.Provides



/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class), (DatabaseModule::class)])
interface ViewModelInjector {
    fun inject(mainViewModel: MainViewModel)

    fun inject(conditionViewModel: ConditionViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder

    }
}