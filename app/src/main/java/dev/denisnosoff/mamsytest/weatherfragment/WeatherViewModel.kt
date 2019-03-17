package dev.denisnosoff.mamsytest.weatherfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.weather.WeatherApiService
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherRealmObject
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherRepository
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherSummaryRealmObject
import dev.denisnosoff.mamsytest.util.state.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import javax.inject.Inject

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val compositeDisposable = CompositeDisposable()

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

    fun request(id: Int) {
        val weatherRequestDisposable = weatherApiService.getWeatherById(id.toString(), App.API_KEY)
            .map {
                WeatherRealmObject(it.city.id, RealmList(*it.list.map { summary ->
                    WeatherSummaryRealmObject(
                        summary.dt,
                        summary.main.temp,
                        summary.weather[0].description,
                        summary.weather[0].icon,
                        summary.wind.speed) }.toTypedArray())) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                saveData(it)
                tryToShowDataFromStorage()
            }, {
                tryToShowDataFromStorage()
            })

        compositeDisposable.add(weatherRequestDisposable)
    }

    private fun showData(id: Int) {
        val showingData = weatherRepository.getData(id)
            .subscribe( {

            }, {
                throw NoSuchElementException("No data in storage")
            } )
        compositeDisposable.add(showingData)
    }

    private fun saveData(weatherRealmObject: WeatherRealmObject) {
        weatherRepository.saveData(weatherRealmObject)
    }

    fun onStop() {
        if (compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}