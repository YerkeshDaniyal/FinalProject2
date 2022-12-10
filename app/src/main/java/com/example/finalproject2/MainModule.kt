package com.example.finalproject2
 
import android.content.Context
import androidx.room.Room
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.repo.MainRepositorylmpl
import com.example.finalproject2.rest.GoogleMapsRetrofitService
import com.example.finalproject2.room.AppDatabase
import com.example.finalproject2.room.WeatherDao
import com.example.finalproject2.rest.WeatherRetrofitConfig
import dagger.Binds
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
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "weather-db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideGoogleMapsRetrofitService(): GoogleMapsRetrofitService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GoogleMapsRetrofitService::class.java)
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsMainRepository(mainRepositorylmpl: MainRepositorylmpl): MainRepository
}

