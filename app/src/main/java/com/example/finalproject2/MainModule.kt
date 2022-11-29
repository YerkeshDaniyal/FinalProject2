package com.example.finalproject2
import android.content.Context
import androidx.room.Room
import com.example.finalproject2.room.AppDatabase
import com.example.finalproject2.room.WeatherDao
import com.example.finalproject2.rest.WeatherRetrofitConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideRetrofitService(): WeatherRetrofitConfig {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WeatherRetrofitConfig::class.java)
        //нужно апдату и дао
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "weather-db").build()
    }
    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}
