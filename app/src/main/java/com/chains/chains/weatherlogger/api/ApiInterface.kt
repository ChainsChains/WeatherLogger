package com.chains.chains.weatherlogger.api

import androidx.lifecycle.LiveData
import com.chains.chains.weatherlogger.util.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun fetchWeather(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): LiveData<ApiResponse<WeatherResponse>>
}