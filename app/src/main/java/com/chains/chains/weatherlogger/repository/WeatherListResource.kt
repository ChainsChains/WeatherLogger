package com.chains.chains.weatherlogger.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chains.chains.weatherlogger.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class WeatherListResource<ResultType, RequestType>() {
    private val result = MutableLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val data = loadFromDb()
        data.observeForever {
            setValue(Resource.success(it))
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun fetchWeather(city: String) {
        val apiResponse = createCall(city)
        apiResponse.observeForever { response ->
            when (response) {
                is ApiSuccessResponse -> {
                    GlobalScope.launch {
                        saveWeatherResult(response)
                    }
                }
                is ApiEmptyResponse -> {

                }
                is ApiErrorResponse -> {

                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(city: String): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun saveWeatherResult(item: ApiSuccessResponse<RequestType>)

}