package com.example.finalproject2.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.finalproject2.R
import com.example.finalproject2.databinding.FragmentHomeBinding
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var repository: MainRepository
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCurCity(DEFAULT_CITY, getString(R.string.places_api_key))
        viewModel.showProgress.observe(viewLifecycleOwner) {
            showProgress(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.city.observe(viewLifecycleOwner, Observer { weather ->
            binding.cityWeather = weather
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

    private fun showProgress(enabled: Boolean) {
        binding.progressCircular.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    companion object {
        private const val DEFAULT_CITY = "Almaty"
    }
}
