package com.example.finalproject2.adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.finalproject2.R
import kotlin.math.roundToInt
@BindingAdapter("android:src", requireAll = false)
fun setThumbnail(thumbnail: ImageView, icon: String?) {
    when (icon) {
        "09d", "10d", "11d", "09n", "10n", "11n" -> thumbnail.setImageResource(R.drawable.rain)
        "01d" -> thumbnail.setImageResource(R.drawable.sun)
        "02d", "03d" -> thumbnail.setImageResource(R.drawable.sun_cloud)
        "01n" -> thumbnail.setImageResource(R.drawable.moon)
        "02n", "03n" -> thumbnail.setImageResource(R.drawable.moon_cloud)
        "04d", "13d", "50d", "04n", "13n", "50n" -> thumbnail.setImageResource(R.drawable.cloud)
    }
}
@BindingAdapter("android:text")
fun setTempText(view: TextView, temp: Float) {
    view.text = view.resources.getString(R.string.temp, temp.roundToInt())
}
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, photoReference: String?) {
    if (photoReference == null) return
    val requestUrl = String.format(
        "https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&key=%s&maxwidth=1200&maxheight=400",
        photoReference,
        view.resources.getString(R.string.places_api_key)
    )
    view.load(requestUrl)
}