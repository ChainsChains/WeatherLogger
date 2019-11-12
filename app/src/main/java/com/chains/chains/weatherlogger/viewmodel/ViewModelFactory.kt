/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chains.chains.weatherlogger.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.chains.chains.weatherlogger.db.WeatherDb
import com.chains.chains.weatherlogger.di.NetworkModule
import com.chains.chains.weatherlogger.repository.WeatherConditionsRepository

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, WeatherDb::class.java, "posts")
                    .build()
            val apiService = NetworkModule.provideApi(NetworkModule.provideRetrofit())
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(WeatherConditionsRepository(db.weatherDao(), apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
