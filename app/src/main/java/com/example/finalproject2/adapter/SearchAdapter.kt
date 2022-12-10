package com.example.finalproject2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2.R
import com.example.finalproject2.model.WeatherApiResult
import androidx.databinding.DataBindingUtil
import com.example.finalproject2.databinding.ItemResultBinding


class SearchAdapter(
    private val onItemClicked: (WeatherApiResult) -> Unit
) : ListAdapter<WeatherApiResult, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private class ResultViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: WeatherApiResult, onItemClicked: (WeatherApiResult) -> Unit) {
            binding.cityWeather = city
            itemView.setOnClickListener {
                onItemClicked(city)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemResultBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_result,
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ResultViewHolder -> {
                holder.bind(getItem(position), onItemClicked)
            }
        }
    }
}

object DiffUtilCallback : DiffUtil.ItemCallback<WeatherApiResult>() {
    override fun areItemsTheSame(oldItem: WeatherApiResult, newItem: WeatherApiResult): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: WeatherApiResult, newItem: WeatherApiResult): Boolean {
        return oldItem == newItem
    }
}


