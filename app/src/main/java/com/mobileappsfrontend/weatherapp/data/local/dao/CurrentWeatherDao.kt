package com.mobileappsfrontend.weatherapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mobileappsfrontend.weatherapp.data.local.entity.CurrentWeatherEntity;

import kotlinx.coroutines.flow.Flow;

@Dao
interface CurrentWeatherDao {
    // No WHERE options needed as there will only ever be one item cached
    @Query("SELECT * FROM current_weather LIMIT 1")
    suspend fun getCachedWeather(): CurrentWeatherEntity?

    // Update and Insert whenever updating
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWeather(weather: CurrentWeatherEntity)

    @Query("""
    SELECT * FROM current_weather 
    WHERE lastUpdatedEpochMillis > :minValidTime
    LIMIT 1
""")
    suspend fun getValidWeather(
        minValidTime: Long
    ): CurrentWeatherEntity?

    @Query("DELETE FROM current_weather")
    suspend fun deleteWeather()
}

