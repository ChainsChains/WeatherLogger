package com.chains.chains.weatherlogger.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chains.chains.weatherlogger.vo.WeatherConditions

@Dao
abstract class WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg weathers: WeatherConditions)

    @Query(
        """
            SELECT * FROM WeatherConditions ORDER BY timestamp DESC"""
    )
    abstract fun getAll(): LiveData<List<WeatherConditions>>

}