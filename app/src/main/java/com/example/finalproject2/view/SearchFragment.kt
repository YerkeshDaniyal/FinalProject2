package com.example.finalproject2.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject2.R
import com.example.finalproject2.repo.MainRepository
import com.example.finalproject2.adapter.SearchAdapter
import com.example.finalproject2.databinding.FragmentHomeBinding
import com.example.finalproject2.databinding.FragmentSearchBinding
import com.example.finalproject2.model.WeatherApiResult
import com.example.finalproject2.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.databinding.DataBindingUtil
 
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
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
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_search,
            container,
            false
        )
        return binding.root
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
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            requireActivity().layoutInflater,
            R.layout.fragment_home,
            null,
            false
        )

        binding.cityWeather = weather
        builder.setView(binding.root)
        builder.create()
        builder.show()

    }

    private fun showProgress(enabled: Boolean) {
        binding.progressCircular.visibility = if (enabled) View.VISIBLE
        else View.GONE
    }

}