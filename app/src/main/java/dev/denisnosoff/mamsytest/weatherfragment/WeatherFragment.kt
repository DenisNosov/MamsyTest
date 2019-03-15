package dev.denisnosoff.mamsytest.weatherfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.mainactivity.MainActivity
import dev.denisnosoff.mamsytest.model.cities.CitiesApiSevice
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.util.hide
import dev.denisnosoff.mamsytest.util.show
import dev.denisnosoff.mamsytest.util.state.Statable
import dev.denisnosoff.mamsytest.util.state.State
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather.view.*
import javax.inject.Inject

class WeatherFragment : Fragment(), Statable{

    override var state: State = State.LOADING
        set(_state: State) {
            field = _state
            changeUI(field)
        }

    override fun changeUI(state: State) {
        when (state) {
            State.LOADING -> {
                progressBar.show()
                errorTextView.hide()
                mainViewGroup.hide()
            }
            State.SUCCESSFUL -> {
                progressBar.hide()
                errorTextView.hide()
                mainViewGroup.show()
            }
            State.ERROR -> {
                progressBar.hide()
                errorTextView.show()
                mainViewGroup.hide()
            }
        }
    }

    private var city : CityItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        city = arguments?.getParcelable(ARG_CITY_ITEM)

        with (view) {
            mainViewGroup.section_label.text = context.getString(R.string.city_name_country_string, city?.name, city?.country)
            mainViewGroup.btnDeleteFragment.setOnClickListener { (activity as MainActivity).deleteFragment(city) }
//            mainViewGroup.section_label.setOnClickListener {
//                state = State.ERROR
//            }
//            progressBar.setOnClickListener {
//                state = State.SUCCESSFUL
//            }
//            errorTextView.setOnClickListener {
//                state = State.LOADING
//            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        state = State.SUCCESSFUL
    }

    companion object {

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