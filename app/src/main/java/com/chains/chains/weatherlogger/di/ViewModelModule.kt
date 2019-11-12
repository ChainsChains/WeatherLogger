package com.chains.chains.weatherlogger.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chains.chains.weatherlogger.viewmodel.MainViewModel
import com.chains.chains.weatherlogger.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindEditPlaceViewModel(editPlaceViewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}