package com.chains.chains.weatherlogger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chains.chains.weatherlogger.vo.WeatherConditions

@Database(
    entities = [
        WeatherConditions::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}