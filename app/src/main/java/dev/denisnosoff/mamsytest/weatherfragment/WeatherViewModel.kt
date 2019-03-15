package dev.denisnosoff.mamsytest.weatherfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.weather.WeatherApiService
import dev.denisnosoff.mamsytest.util.state.State
import javax.inject.Inject

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var weatherApiService: WeatherApiService

    var state = MutableLiveData<State>()

    init {
        (app as App).appComponent.inject(this)
        state.value = State.LOADING
    }

    fun request(id: Int) {
        weatherApiService.getWeatherById(id.toString(), App.API_KEY)
    }
}