package com.example.finalproject2.repo

import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.rest.WeatherRetrofitConfig
import com.example.finalproject2.room.WeatherDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val retrofitService: WeatherRetrofitConfig
) {

    suspend fun fetchCity(city: String): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofitService.fetchCity(city)
                if (response.isSuccessful) {
                    val searchedCity = response.body() ?: return@withContext Resource.Success(Unit)
                    insertSearchedCity(searchedCity)
                    Resource.Success(Unit)
                } else {
                    Resource.Error("City not found")
                }
            } catch (e: Exception) {
                Resource.Error("Server error")
            }
        }
    }

    suspend fun fetchLocationPhone(lat: String, lon: String): Resource<WeatherApiResult?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofitService.fetchLocationPhone(lat, lon)
                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    Resource.Error("Location Not Found")
                }
            } catch (e: Exception) {
                Resource.Error("Server error")
            }
        }
    }

    fun getAllSearchedCities() = weatherDao.getAll()

    suspend fun insertSearchedCity(searchedCity: WeatherApiResult) {
        weatherDao.insertCity(searchedCity)
    }
}