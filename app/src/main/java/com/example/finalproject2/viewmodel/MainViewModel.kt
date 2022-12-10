package com.example.finalproject2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject2.repo.Resource
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.model.WeatherApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
    ) : ViewModel() {
    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()
    val showProgress = MutableLiveData(false)

    fun fetchCurCity(curCity: String, apiKey: String) {
        showProgress.postValue(true)
        viewModelScope.launch {
            when (val response = repository.fetchCity(curCity, apiKey)) {
                is Resource.Success -> {
                    val cityResponse = response.data ?: return@launch
                    city.postValue(cityResponse)
                    showProgress.postValue(false)
                }
                is Resource.Error -> errorMessage.postValue(response.message.toString())
            }
        }

    }}