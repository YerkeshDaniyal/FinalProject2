package com.example.finalproject2.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_result")
data class WeatherApiResult(
    @Embedded val coord: Coordiante,
    val weather: List<Weather>,
    @Embedded val sys: Sys,
    @Embedded val main: Main,
    @Embedded val wind: Wind,
    @PrimaryKey val name: String
)

data class Wind(
    val speed: Float
)

@Entity
data class Weather(
    @PrimaryKey val id: Int,
    val cityName: String,
    val main: String,
    val description: String,
    val icon: String
)

data class Coordiante(
    val lon: Double,
    val lat: Double
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val humidity: Int,
)

data class Sys(val country: String)