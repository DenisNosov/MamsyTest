package dev.denisnosoff.mamsytest.weatherfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.util.hide
import dev.denisnosoff.mamsytest.util.show
import dev.denisnosoff.mamsytest.util.state.Statable
import dev.denisnosoff.mamsytest.util.state.State
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather.view.*

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


    private var city : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        city = arguments?.getString(ARG_NAME)

        view.mainViewGroup.section_label.text = city

//        view.mainViewGroup.section_label.setOnClickListener {
//            state = State.ERROR
//        }
//        view.progressBar.setOnClickListener {
//            state = State.SUCCESSFUL
//        }
//        view.errorTextView.setOnClickListener {
//            state = State.LOADING
//        }

        return view
    }

    companion object {

        private const val ARG_NAME = "CITY"

        fun newInstance(name: String) : Fragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putString(ARG_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }

}