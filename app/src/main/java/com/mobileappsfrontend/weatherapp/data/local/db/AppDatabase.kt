package com.mobileappsfrontend.weatherapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileappsfrontend.weatherapp.data.local.dao.CurrentWeatherDao
import com.mobileappsfrontend.weatherapp.data.local.entity.CurrentWeatherEntity

@Database(
    entities = [CurrentWeatherEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
}