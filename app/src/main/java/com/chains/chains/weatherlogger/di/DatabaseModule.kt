package com.chains.chains.weatherlogger.di

import android.app.Application
import androidx.room.Room
import com.chains.chains.weatherlogger.db.WeatherDao
import com.chains.chains.weatherlogger.db.WeatherDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDb {
        return Room
            .databaseBuilder(app, WeatherDb::class.java, "weatherapp.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb): WeatherDao {
        return db.weatherDao()
    }
}