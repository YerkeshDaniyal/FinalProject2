package com.example.finalproject2.rest



import retrofit2.http.*
import com.example.finalproject2.model.WeatherApiResult
import retrofit2.Response

interface WeatherRetrofitConfig {

    //weather?q={city}&units=metric&appid=fccbdc41f2bb5a0b09266288a1a820ce&lang=pt_br
    @GET("weather")
   suspend fun fetchCity(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = "fccbdc41f2bb5a0b09266288a1a820ce",
        @Query("lang") lang: String = "en_us"

    ): Response<WeatherApiResult>

    //https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
//    @GET("weather")
//    suspend fun fetchLocationPhone(
//        @Query("lat") lat: String,
//        @Query("lon") lon: String,
//        @Query("appid") appid: String = "fccbdc41f2bb5a0b09266288a1a820ce",
//        @Query("units") units: String = "metric",
//        @Query("lang") lang: String = "en_us"
//    ): Response<WeatherApiResult>


//    companion object {
//
//        val retrofitService: WeatherRetrofitConfig by lazy {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            retrofit.create(WeatherRetrofitConfig::class.java)
//
//        }
//
//        fun getInstance(): WeatherRetrofitConfig {
//            return retrofitService
//        }
//
//    }
}