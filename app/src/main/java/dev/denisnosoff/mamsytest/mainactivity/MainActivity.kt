package dev.denisnosoff.mamsytest.mainactivity

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.weatherfragment.WeatherFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_weather.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    private val citiesList = arrayListOf("Moscow", "Los Angeles", "New-York")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()

        fab.setOnClickListener {
            mSectionsPagerAdapter.addItem(WeatherFragment.newInstance("HEEY"))
        }
    }

    private fun initViewPager() {
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        for (city in citiesList) {
            mSectionsPagerAdapter.addItem(WeatherFragment.newInstance(city.toUpperCase()))
        }

        tabLayout.setupWithViewPager(container)
        container.adapter = mSectionsPagerAdapter
    }

//    fun setTabName(name: String, )
}
