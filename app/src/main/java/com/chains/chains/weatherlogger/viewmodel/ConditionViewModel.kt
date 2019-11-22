package com.chains.chains.weatherlogger.viewmodel

import androidx.lifecycle.MutableLiveData
import com.chains.chains.weatherlogger.vo.WeatherConditions

class ConditionViewModel : BaseViewModel() {
    private val conditionCity = MutableLiveData<String>()
    private val conditionTemp = MutableLiveData<String>()
    private val conditionTime = MutableLiveData<String>()

    fun bind(conditions: WeatherConditions) {
        conditionCity.value = conditions.city
        conditionTemp.value = conditions.temp
        conditionTime.value = conditions.timestamp
    }

    fun getCity() = conditionCity
    fun getTemp() = conditionTemp
    fun getTime() = conditionTime
}