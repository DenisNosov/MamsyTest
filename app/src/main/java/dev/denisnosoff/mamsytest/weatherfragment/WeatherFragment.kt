package dev.denisnosoff.mamsytest.weatherfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.mainactivity.MainActivity
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherSummaryRealmObject
import dev.denisnosoff.mamsytest.util.hide
import dev.denisnosoff.mamsytest.util.show
import dev.denisnosoff.mamsytest.util.state.Statable
import dev.denisnosoff.mamsytest.util.state.State
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather.view.*
import kotlinx.android.synthetic.main.layout_weather.*

class WeatherFragment : Fragment(), Statable{

    override var state: State = State.LOADING
        set(_state) {
            field = _state
            changeUI(field)
        }

    override fun changeUI(state: State) {
        when (state) {
            State.LOADING -> {
                pbWeather.show()
                tvWeatherError.hide()
                mainViewGroup.hide()
            }
            State.SUCCESSFUL -> {
                pbWeather.hide()
                tvWeatherError.hide()
                mainViewGroup.show()
            }
            State.ERROR -> {
                pbWeather.hide()
                tvWeatherError.show()
                mainViewGroup.hide()
            }
        }
    }

    private lateinit var city : CityItem
    private lateinit var mViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        city = arguments?.getParcelable(ARG_CITY_ITEM)!!

        initViewModel()

        with (view) {
            btnDeleteFragment.setOnClickListener { (activity as MainActivity).deleteFragment(city) }
        }
        return view
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this)[WeatherViewModel::class.java]
        mViewModel.state.observe(this, Observer { state = it })
        mViewModel.error.observe(this, Observer { tvWeatherError.text = it })
        mViewModel.currentWeather.observe(this, Observer { updateCurrentWeather(it) })
        mViewModel.futureWeather.observe(this, Observer { updateFutureWeather(it) })
        mViewModel.request(city)
    }

    private fun updateFutureWeather(weatherList: List<WeatherSummaryRealmObject>?) {
        weatherList?.let {
            val adapter = WeatherRVAdapter(it)
            rvFutureWeather.layoutManager = LinearLayoutManager(this.context)
            rvFutureWeather.adapter = adapter
        }
    }

    private fun updateCurrentWeather(weather: WeatherSummaryRealmObject?) {
        weather?.let {
            Glide.with(this)
                .load("$BASE_IMAGE_URL${weather.icon}.png")
                .into(ivCurrentWeather)
            tvCurrentTemp.text = getString(R.string.temp_string, Math.round(weather.temp))
            tvCurrentDescription.text = weather.description.capitalize()
            tvCurrentWind.text = getString(R.string.wind_string, Math.round(weather.windSpeed))
        }
    }

    override fun onStart() {
        super.onStart()
        state = State.SUCCESSFUL
    }

    override fun onStop() {
        mViewModel.onStop()
        super.onStop()
    }

    companion object {

        const val BASE_IMAGE_URL = "http://openweathermap.org/img/w/"
        private const val ARG_CITY_ITEM = "CITY"

        fun newInstance(item: CityItem) : Fragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putParcelable(ARG_CITY_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

}