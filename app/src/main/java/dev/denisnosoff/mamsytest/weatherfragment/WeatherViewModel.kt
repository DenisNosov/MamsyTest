package dev.denisnosoff.mamsytest.weatherfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.model.weather.WeatherApiService
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherRealmObject
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherRepository
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherSummaryRealmObject
import dev.denisnosoff.mamsytest.util.state.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import java.util.*
import javax.inject.Inject

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var city: CityItem

    @Inject
    lateinit var weatherApiService: WeatherApiService

    @Inject
    lateinit var weatherRepository: WeatherRepository

    var state = MutableLiveData<State>()
    var error = MutableLiveData<String>()
    var currentWeather = MutableLiveData<WeatherSummaryRealmObject>()
    var futureWeather = MutableLiveData<List<WeatherSummaryRealmObject>>()

    init {
        (app as App).appComponent.inject(this)
        state.value = State.LOADING
        error.value = "Unknown error"
    }

    fun request(item: CityItem?) {
        item?.let { this.city = item }
        val weatherRequestDisposable = weatherApiService.getWeatherById(id = item!!.id.toString(),apiKey =  App.API_KEY)
            .map {
                WeatherRealmObject(it.city.id, RealmList(*it.list.map { summary ->
                    WeatherSummaryRealmObject(
                        summary.dt,
                        summary.main.temp,
                        summary.weather[0].description,
                        summary.weather[0].icon,
                        summary.wind.speed
                    ) }.toTypedArray())) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                saveData(it)
                showData(it)
            }, {
                tryToShowDataFromStorage(city.id)
            })

        compositeDisposable.add(weatherRequestDisposable)
    }

    private fun tryToShowDataFromStorage(cityId: Int) {
        try {
            val weatherObject = weatherRepository.getData(cityId)
            val weatherNew = weatherObject.weatherSummaries.filter { (it.date*1000) >= Calendar.getInstance().timeInMillis }
            try {
                currentWeather.value = weatherNew[0]
                futureWeather.value = weatherNew.subList(1, weatherNew.size)
                state.value = State.SUCCESSFUL
            } catch (e: IndexOutOfBoundsException) {
                error.value = "Can't get new data from server. Saved data is too old."
                state.value = State.ERROR
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            error.value = "Can't find saved weather or get new data"
            state.value = State.ERROR
        }
    }

    private fun showData(weather: WeatherRealmObject) {
        currentWeather.value = weather.weatherSummaries[0]
        futureWeather.value = weather.weatherSummaries.subList(1, weather.weatherSummaries.size)
        state.value = State.SUCCESSFUL
    }

    private fun saveData(weatherRealmObject: WeatherRealmObject) {
        weatherRepository.saveData(weatherRealmObject)
    }

    fun onStop() {
        if (compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}