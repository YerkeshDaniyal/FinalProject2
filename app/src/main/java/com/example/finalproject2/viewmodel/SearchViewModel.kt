package com.example.finalproject2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject2.model.IViewProgress
import com.example.finalproject2.model.MainRepository
import com.example.finalproject2.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val view: IViewProgress, private val repository: MainRepository): ViewModel() {

    val searchCity = MutableLiveData<WeatherApiResult>()
    val errorMessage = MutableLiveData<String>()

    fun fetchCity(city: String) {
        view.showProgress(true)
        val request = repository.fetchCity(city)


    }
}