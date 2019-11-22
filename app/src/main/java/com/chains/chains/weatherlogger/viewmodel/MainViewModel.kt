package com.chains.chains.weatherlogger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chains.chains.weatherlogger.contract.MainContract
import com.chains.chains.weatherlogger.repository.WeatherConditionsRepository
import com.chains.chains.weatherlogger.ui.RwAdapter
import com.chains.chains.weatherlogger.util.Resource
import com.chains.chains.weatherlogger.vo.WeatherConditions
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: WeatherConditionsRepository
) : BaseViewModel(), MainContract.ViewModel {

    private val loadingVisibility = MutableLiveData<Int>()
    private val weatherData = repository.loadAll()
    val rwAdapter: RwAdapter = RwAdapter()

    override fun fetchWeatherData(city: String) {
        repository.fetchWeather(city)
    }

    override fun updateAdapterData(resource: Resource<List<WeatherConditions>>) {
        resource.data?.let { rwAdapter.setData(it) }
    }

    override fun subscribeWeatherData(): LiveData<Resource<List<WeatherConditions>>> {
        return weatherData
    }

    override fun subscribeLoadingVisibility(): MutableLiveData<Int> {
        return loadingVisibility
    }
}