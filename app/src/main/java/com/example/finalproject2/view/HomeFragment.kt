package com.example.finalproject2.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.finalproject2.R
import com.example.finalproject2.databinding.FragmentHomeBinding
import com.example.finalproject2.model.IViewProgress
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), IViewProgress {

    private var binding: FragmentHomeBinding? = null

    @Inject
    lateinit var repository: MainRepository
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        viewModel.fetchCurCity(DEFAULT_CITY, getString(R.string.places_api_key))
        viewModel.showProgress.observe(viewLifecycleOwner) {
            showProgress(it)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.city.observe(viewLifecycleOwner, Observer { weather ->

            binding?.let {
                with(it) {
                    homeTxtCity.text = weather.name

                    homeTxtWeather.text = weather.weather[0].main
                    homeTxtStats.text = weather.weather[0].description

                    homeTxtTemp.text = "${weather.main.temp.roundToInt()}C°"
                    homeTxtFeelLike.text = "${weather.main.feels_like.roundToInt()}C°"
                    homeTxtHumidity.text = "${weather.main.humidity}%"
                    homeTxtWind.text = "${weather.wind.speed} m/s"

                    when (weather.weather[0].icon) {
                        "09d", "10d", "11d", "09n", "10n", "11n" -> binding?.homeImgNow?.setImageResource(
                            R.drawable.rain
                        )
                        "01d" -> binding?.homeImgNow?.setImageResource(R.drawable.sun)
                        "02d", "03d" -> binding?.homeImgNow?.setImageResource(R.drawable.sun_cloud)
                        "01n" -> binding?.homeImgNow?.setImageResource(R.drawable.moon)
                        "02n", "03n" -> binding?.homeImgNow?.setImageResource(R.drawable.moon_cloud)
                        "04d", "13d", "50d", "04n", "13n", "50n" -> binding?.homeImgNow?.setImageResource(
                            R.drawable.cloud
                        )
                    }
                }
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun showProgress(enabled: Boolean) {
        if (enabled) binding?.progressCircular?.visibility = View.VISIBLE
        else binding?.progressCircular?.visibility = View.GONE
    }

    companion object {
        private const val DEFAULT_CITY = "Almaty"
    }
}