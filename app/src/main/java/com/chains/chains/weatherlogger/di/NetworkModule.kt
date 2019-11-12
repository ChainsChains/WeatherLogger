package com.chains.chains.weatherlogger.di

import com.chains.chains.weatherlogger.api.ApiInterface
import com.chains.chains.weatherlogger.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    const val appid = "299efdaf30a2f4ccbfbde2b1adbfa841"
    const val units = "metric"

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
}