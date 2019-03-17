package dev.denisnosoff.mamsytest.weatherfragment

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherSummaryRealmObject
import kotlinx.android.synthetic.main.weather_item.view.*
import java.util.*

class WeatherRVAdapter(private val weatherList: List<WeatherSummaryRealmObject>) : RecyclerView.Adapter<WeatherRVAdapter.WeatherViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false))

    override fun getItemCount() = weatherList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    class WeatherViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: WeatherSummaryRealmObject) {
            Glide.with(view)
                .load("${WeatherFragment.BASE_IMAGE_URL}${weather.icon}.png")
                .into(view.ivFutureWeather)
            view.tvFutureTemp.text = view.context.getString(R.string.temp_string, Math.round(weather.temp))
            view.tvFutureDescription.text = weather.description.capitalize()
            view.tvFutureWind.text = view.context.getString(R.string.wind_string, Math.round(weather.windSpeed))
            val date = DateFormat.format("dd.MM.yyyy hh:mm a", Date(weather.date*1000))
            view.tvFutureDate.text = date
        }
    }
}