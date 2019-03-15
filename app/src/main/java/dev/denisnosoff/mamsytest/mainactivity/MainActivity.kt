package dev.denisnosoff.mamsytest.mainactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.searchactivity.SearchActivity
import dev.denisnosoff.mamsytest.weatherfragment.WeatherFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
    private lateinit var mViewModel: MainViewModel

    private var citiesList: ArrayList<CityItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
        initViewModel()
        initFab()
    }

    private fun initFab() {
        fab.setOnClickListener {
            SearchActivity.startForResult(SearchActivity.SEARCH_ACTIVITY_REQUEST_CODE, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SearchActivity.SEARCH_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            data?.let {
                val cityItem = data.getParcelableExtra<CityItem>(SearchActivity.NEW_CITY_ITEM)
                mViewModel.addCity(cityItem)
            }

    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        mViewModel.citiesListLiveData.observe(this, Observer {
            refreshViewPager(it)
        })
    }

    private fun initViewPager() {
        tabLayout.setupWithViewPager(container)
        container.adapter = mSectionsPagerAdapter
    }

    private fun refreshViewPager(cities: List<CityItem>) {
        Log.d(TAG, "refreshing view pager $cities")
        for (city in cities)
            if (!citiesList.contains(city)) {
                mSectionsPagerAdapter.addItem(WeatherFragment.newInstance(city))
                citiesList.add(city)
                Log.d(TAG, "${citiesList.indexOf(city)}")
                setTabName(city.name, citiesList.indexOf(city))
            }
    }

    override fun onPause() {
        mViewModel.onPause()
        super.onPause()
    }

    fun setTabName(name: String, pos: Int) {
        tabLayout.getTabAt(pos)?.text = name
    }
}
