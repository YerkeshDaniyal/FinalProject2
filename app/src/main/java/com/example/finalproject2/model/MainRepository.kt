package com.example.finalproject2.model

import com.example.finalproject2.rest.WeatherRetrofitConfig

class MainRepository(private val retrofitService: WeatherRetrofitConfig) {

    fun fetchCity(city: String) = retrofitService.fetchCity(city)

    fun fetchLocationPhone(lat: String, lon: String) = retrofitService.fetchLocationPhone(lat, lon)
}