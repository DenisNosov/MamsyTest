package dev.denisnosoff.mamsytest.mainactivity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.weatherfragment.WeatherFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
    private lateinit var mViewModel: MainViewModel

    private var citiesList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        initViewPager()

        mViewModel.citiesListLiveData.observe(this, Observer {
            refreshViewPager(it)
        })

        fab.setOnClickListener {
//            mViewModel.addCity("Lmao")
        }
    }

    private fun refreshViewPager(cities: List<String>) {
        Log.d(TAG, "refreshing view pager ${cities.toString()}")
        for (city in cities)
            if (!citiesList.contains(city)) {
                mSectionsPagerAdapter.addItem(WeatherFragment.newInstance(city))
                citiesList.add(city)

            }
    }

    private fun initViewPager() {
        tabLayout.setupWithViewPager(container)
        container.adapter = mSectionsPagerAdapter
    }

    override fun onPause() {
        mViewModel.onPause()
        super.onPause()
    }

    fun setTabName(name: String, pos: Int) {
        tabLayout.getTabAt(pos)?.text = name
    }
}
