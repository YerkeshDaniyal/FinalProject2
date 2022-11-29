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
import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
 

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val searchCities: LiveData<List<WeatherApiResult>> = repository.getAllSearchedCities()
    val errorMessage = MutableLiveData<String>()
    val showProgress = MutableLiveData(false)

 
    fun fetchCity(city: String, apiKey: String) {
        showProgress.postValue(true)
        viewModelScope.launch {
            showProgress.postValue(false)
            when (val response = repository.fetchCity(city, apiKey)) {
                is Resource.Success -> {
               /**все должно быть хорошо**/
 
    fun fetchCity(city: String) {
        showProgress.postValue(true)
        viewModelScope.launch {
            showProgress.postValue(false)
            when (val response = repository.fetchCity(city)) {
                is Resource.Success -> {
                    /** everything is good **/
 
                }
                is Resource.Error -> errorMessage.postValue(response.message.toString())
            }
        }
 
    }

}