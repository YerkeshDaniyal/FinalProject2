package com.example.finalproject2.repo
import com.example.finalproject2.model.PhotoReferenceResponse
import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.rest.GoogleMapsRetrofitService 
import com.example.finalproject2.rest.WeatherRetrofitConfig
import com.example.finalproject2.room.WeatherDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
 

import androidx.lifecycle.LiveData
import com.example.finalproject2.model.WeatherApiResult


interface MainRepository {
    suspend fun fetchCity(city: String, apiKey: String): Resource<WeatherApiResult?>

    fun getAllSearchedCities(): LiveData<List<WeatherApiResult>>

    suspend fun insertSearchedCity(searchedCity: WeatherApiResult)
}