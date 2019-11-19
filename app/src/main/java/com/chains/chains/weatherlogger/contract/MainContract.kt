package com.chains.chains.weatherlogger.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chains.chains.weatherlogger.util.Resource
import com.chains.chains.weatherlogger.vo.WeatherConditions

interface BaseView<T> {
    var viewModel: T
    fun onError(errorMessage: String)
    fun onDataLoading(visibility: Int)
}

interface MainContract {
    interface View : BaseView<ViewModel> {
        fun onWeatherDataLoaded(resource: Resource<List<WeatherConditions>>)
        fun onSaveClicked(view: android.view.View)
    }

    interface ViewModel {
        fun fetchWeatherData(city: String)
        fun subscribeWeatherData(): LiveData<Resource<List<WeatherConditions>>>
        fun subscribeLoadingVisibility(): MutableLiveData<Int>
    }
}