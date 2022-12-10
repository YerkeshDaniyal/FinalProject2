package com.example.finalproject2.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject2.model.WeatherApiResult

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_result")
    fun getAll(): LiveData<List<WeatherApiResult>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: WeatherApiResult)
}