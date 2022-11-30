package com.example.finalproject2.repo

import com.example.finalproject2.model.PhotoReferenceResponse
import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.rest.GoogleMapsRetrofitService
import com.example.finalproject2.rest.WeatherRetrofitConfig
import com.example.finalproject2.room.WeatherDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val photoService: GoogleMapsRetrofitService,
    private val retrofitService: WeatherRetrofitConfig
) {

    suspend fun fetchCity(city: String, apiKey: String): Resource<WeatherApiResult?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofitService.fetchCity(city)
                if (response.isSuccessful) {
                    val searchedCity = response.body() ?: return@withContext Resource.Success(null)
                    val photoResponse = photoService.fetchPhotoReference(searchedCity.name, apiKey)
                    if (photoResponse.isSuccessful) {
                        val places = (photoResponse.body() as PhotoReferenceResponse).candidates
                        if (places.isNotEmpty()) {
                            val photoReferences = places[0].photos
                            if (photoReferences.isNotEmpty()) {
                                searchedCity.photoReference = photoReferences[0].photoReference
                            }
                        }
                    }
                    insertSearchedCity(searchedCity)
                    Resource.Success(searchedCity)
                } else {
                    Resource.Error("City not found")
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