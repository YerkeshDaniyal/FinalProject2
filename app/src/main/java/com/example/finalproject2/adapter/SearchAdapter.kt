package com.example.finalproject2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2.R
import com.example.finalproject2.model.WeatherApiResult
import kotlinx.android.synthetic.main.item_result.view.*
import kotlin.math.roundToInt

class SearchAdapter(
    private val onItemClicked: (WeatherApiResult) -> Unit
) : ListAdapter<WeatherApiResult, RecyclerView.ViewHolder>(DiffUtilCallback) {
    private class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cityName: TextView = itemView.item_txt_city
        val country: TextView = itemView.item_txt_country
        val status: TextView = itemView.item_txt_status
        val temp: TextView = itemView.item_txt_temp
        val thumbnail: ImageView = itemView.item_img_temp

        fun bind(city: WeatherApiResult, onItemClicked: (WeatherApiResult) -> Unit) {

            cityName.text = city.name
            country.text = city.sys.country
            status.text = city.weather[0].main
            temp.text = "C° ${city.main.temp.roundToInt()}"

            when (city.weather[0].icon) {
                "09d", "10d", "11d", "09n", "10n", "11n" -> thumbnail.setImageResource(R.drawable.rain)
                "01d" -> thumbnail.setImageResource(R.drawable.sun)
                "02d", "03d" -> thumbnail.setImageResource(R.drawable.sun_cloud)
                "01n" -> thumbnail.setImageResource(R.drawable.moon)
                "02n", "03n" -> thumbnail.setImageResource(R.drawable.moon_cloud)
                "04d", "13d", "50d", "04n", "13n", "50n" -> thumbnail.setImageResource(R.drawable.cloud)
            }

            itemView.setOnClickListener {
                onItemClicked(city)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
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