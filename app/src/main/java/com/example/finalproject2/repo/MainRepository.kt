package com.example.finalproject2.repo

import androidx.lifecycle.LiveData
import com.example.finalproject2.model.WeatherApiResult


interface MainRepository {
    suspend fun fetchCity(city: String, apiKey: String): Resource<WeatherApiResult?>

    fun getAllSearchedCities(): LiveData<List<WeatherApiResult>>

    suspend fun insertSearchedCity(searchedCity: WeatherApiResult)
}