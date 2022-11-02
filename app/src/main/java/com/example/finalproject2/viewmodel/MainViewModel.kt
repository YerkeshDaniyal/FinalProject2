package com.example.finalproject2.viewmodel 
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject2.model.IViewProgress
import com.example.finalproject2.model.MainRepository
import com.example.finalproject2.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
 

class MainViewModel(val view: IViewProgress, private val repository: MainRepository) : ViewModel() {

    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()
    var requestLocation = MutableLiveData<Boolean>()

    fun locationPhone(lat: String, lon: String) {

class MainViewModel(val view: IViewProgress, private val repository: MainRepository) : ViewModel() {
    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()
    var requestLocation = MutableLiveData<Boolean>()

    fun locationPhone(lat: String, lon: String) {

 
        view.showProgress(true)
        val request = repository.fetchLocationPhone(lat, lon)

        request.enqueue(object : Callback<WeatherApiResult> {
            override fun onResponse(
                call: Call<WeatherApiResult>,
                response: Response<WeatherApiResult>
            ) {
                if (response.isSuccessful){
                    city.postValue(response.body())
                    view.showProgress(false)
                }
                else
                    errorMessage.postValue("Location Not Found")
            }

            override fun onFailure(call: Call<WeatherApiResult>, t: Throwable) {
                errorMessage.postValue("Server error")
            }

        })
 

    }
 
    fun requestPermissionGranted(){
        view.showProgress(false)
        requestLocation.value = true
    }
}
 

    fun requestPermissionGranted(){
        view.showProgress(false)
        requestLocation.value = true
    }
}
 
 
