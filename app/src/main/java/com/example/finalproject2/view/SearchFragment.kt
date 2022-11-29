package com.example.finalproject2.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
 
import coil.load
import com.example.finalproject2.R
import com.example.climatyweather.viewmodel.SearchViewModel

import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.R
import com.example.finalproject2.adapter.SearchAdapter
import com.example.finalproject2.databinding.FragmentSearchBinding
import com.example.finalproject2.model.IViewProgress
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.adapter.SearchAdapter
import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt
 
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), IViewProgress {
    private var _binding: FragmentSearchBinding? = null

 
    //  только onCreateView между и между
    // This property is only valid between onCreateView and
 
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter

    @Inject
    lateinit var repository: MainRepository
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter { weather -> openDialog(weather) }
        binding.searchRv.adapter = adapter
        viewModel.showProgress.observe(viewLifecycleOwner) {
            showProgress(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.searchCities.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.searchSrc.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(submit: String?): Boolean {
                viewModel.fetchCity(submit.toString(), getString(R.string.places_api_key))
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })

    }

    private fun openDialog(weather: WeatherApiResult) {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater.inflate(R.layout.fragment_home, null)

        val txtCity = inflater.findViewById<TextView>(R.id.home_txt_city)
        val imgTemp = inflater.findViewById<ImageView>(R.id.home_img_now)
        val txtTemp = inflater.findViewById<TextView>(R.id.home_txt_temp)
        val txtWeather = inflater.findViewById<TextView>(R.id.home_txt_weather)
        val txtStatus = inflater.findViewById<TextView>(R.id.home_txt_stats)
        val txtFeelsLike = inflater.findViewById<TextView>(R.id.home_txt_feelLike)
        val txtHumidity = inflater.findViewById<TextView>(R.id.home_txt_humidity)
        val txtWind = inflater.findViewById<TextView>(R.id.home_txt_wind)
        val imgCity = inflater.findViewById<ImageView>(R.id.home_city_photo)

        txtCity.text = weather.name
        txtTemp.text = "${weather.main.temp.roundToInt()}C°"
        txtWeather.text = weather.weather[0].main
        txtStatus.text = weather.weather[0].description
        txtFeelsLike.text = "${weather.main.feels_like.roundToInt()}C°"
        txtHumidity.text = "${weather.main.humidity}%"
        txtWind.text = "${weather.wind.speed} m/s"
        val requestUrl = String.format(
            "https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&key=%s&maxwidth=1400&maxheight=600",
            weather.photoReference,
            getString(R.string.places_api_key)
        )
        imgCity.load(requestUrl)

        when (weather.weather[0].icon) {
            "09d", "10d", "11d", "09n", "10n", "11n" -> imgTemp.setImageResource(
                R.drawable.rain
            )
            "01d" -> imgTemp.setImageResource(R.drawable.sun)
            "02d", "03d" -> imgTemp.setImageResource(R.drawable.sun_cloud)
            "01n" -> imgTemp.setImageResource(R.drawable.moon)
            "02n", "03n" -> imgTemp.setImageResource(R.drawable.moon_cloud)
            "04d", "13d", "50d", "04n", "13n", "50n" -> imgTemp.setImageResource(
                R.drawable.cloud
            )
        }

        builder.setView(inflater)
        builder.create()
        builder.show()

    }

    override fun showProgress(enabled: Boolean) {
        if (enabled) binding.progressCircular.visibility = View.VISIBLE
        else binding.progressCircular.visibility = View.GONE
    }
}