package dev.denisnosoff.mamsytest.mainactivity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.weatherfragment.WeatherFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
    private lateinit var mViewModel: MainViewModel

    private val citiesList = hashSetOf("Moscow", "Los Angeles", "New-York")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        initViewPager()
        mViewModel.citiesSet.observe(this, Observer {
            refreshViewPager(it)
        })



        fab.setOnClickListener {
            mViewModel.addCity("Hey")
        }
    }

    private fun refreshViewPager(cities: Set<String>) {
        for (city in cities)
            mSectionsPagerAdapter.addItem(WeatherFragment.newInstance(city))
    }

    private fun initViewPager() {

        tabLayout.setupWithViewPager(container)
        container.adapter = mSectionsPagerAdapter
    }

//    fun setTabName(name: String, )
}
