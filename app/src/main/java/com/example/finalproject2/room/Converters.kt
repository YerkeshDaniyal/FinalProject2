package com.example.finalproject2.room

import androidx.room.TypeConverter
import com.example.finalproject2.model.Weather
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Weather>? {
        val listType: Type? = object : TypeToken<List<Weather>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWeatherList(list: List<Weather?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}
