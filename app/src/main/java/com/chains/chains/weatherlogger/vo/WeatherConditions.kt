package com.chains.chains.weatherlogger.vo

import androidx.room.Entity

@Entity(
    tableName = "WeatherConditions",
    primaryKeys = ["timestamp"]
)
data class WeatherConditions(
    val city: String,
    val temp: String,
    val timestamp: String
)