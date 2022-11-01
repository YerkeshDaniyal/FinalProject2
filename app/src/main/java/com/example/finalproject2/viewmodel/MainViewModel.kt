package com.example.finalproject2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject2.model.IViewProgress
import com.example.finalproject2.model.MainRepository
import com.example.finalproject2.model.WeatherApiResult

class MainViewModel(val view: IViewProgress, private val repository: MainRepository) : ViewModel() {
    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()
    var requestLocation = MutableLiveData<Boolean>()

}
