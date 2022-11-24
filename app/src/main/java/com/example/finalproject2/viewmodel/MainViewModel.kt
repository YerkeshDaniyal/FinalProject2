package com.example.finalproject2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()
    var requestLocation = MutableLiveData<Boolean>()
    val showProgress = MutableLiveData(false)

    fun locationPhone(lat: String, lon: String) {
        showProgress.postValue(true)
        viewModelScope.launch {
            when (val request = repository.fetchLocationPhone(lat, lon)) {
                is Resource.Success -> {
                    val curCity = request.data ?: return@launch
                    city.postValue(curCity)
                    showProgress.postValue(false)
                }
                is Resource.Error -> errorMessage.postValue("Error")
            }
        }
    }

    fun requestPermissionGranted() {
        showProgress.postValue(false)
        requestLocation.value = true
    }
}