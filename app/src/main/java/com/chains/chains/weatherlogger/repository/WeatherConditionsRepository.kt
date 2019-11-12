package com.chains.chains.weatherlogger.repository

import androidx.lifecycle.LiveData
import com.chains.chains.weatherlogger.api.ApiInterface
import com.chains.chains.weatherlogger.api.WeatherResponse
import com.chains.chains.weatherlogger.db.WeatherDao
import com.chains.chains.weatherlogger.di.NetworkModule.appid
import com.chains.chains.weatherlogger.di.NetworkModule.units
import com.chains.chains.weatherlogger.util.ApiResponse
import com.chains.chains.weatherlogger.util.ApiSuccessResponse
import com.chains.chains.weatherlogger.util.Resource
import com.chains.chains.weatherlogger.vo.WeatherConditions
import javax.inject.Inject

class WeatherConditionsRepository @Inject constructor(
    private val db: WeatherDao,
    private val weatherService: ApiInterface
) {

    private val resource: WeatherListResource<List<WeatherConditions>, WeatherResponse> by lazy {
        object : WeatherListResource<List<WeatherConditions>, WeatherResponse>() {
            override fun loadFromDb(): LiveData<List<WeatherConditions>> {
                return db.getAll()
            }

            override fun createCall(city: String): LiveData<ApiResponse<WeatherResponse>> {
                return weatherService.fetchWeather(city, appid, units)
            }

            override fun saveWeatherResult(item: ApiSuccessResponse<WeatherResponse>) {
                db.insert(
                    WeatherConditions(
                        item.body.name,
                        item.body.main.temp.toString(),
                        item.body.dt.toString()
                    )
                )
            }
        }
    }

    fun loadAll(): LiveData<Resource<List<WeatherConditions>>> {
        return resource.asLiveData()
    }

    fun fetchWeather(city: String) {
        resource.fetchWeather(city)
    }
}