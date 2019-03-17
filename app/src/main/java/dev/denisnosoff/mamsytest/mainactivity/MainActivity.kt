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
            refreshItems(it)
        })
    }

    private fun initViewPager() {
        tabLayout.setupWithViewPager(container)
        container.adapter = mSectionsPagerAdapter
    }

    private fun refreshItems(cities: List<CityItem>) {
        for (city in cities)
            if (!citiesList.contains(city))
                refreshViewPager(city)
        for (city in citiesList)
            if (!cities.contains(city))
                citiesList = ArrayList(cities)
        refreshTabs()
    }

    private fun refreshViewPager(city: CityItem) {
        mSectionsPagerAdapter.addItem(WeatherFragment.newInstance(city))
        citiesList.add(city)
    }

    override fun onPause() {
        mViewModel.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mViewModel.onDestroy()
        super.onDestroy()
    }

    private fun refreshTabs() {
        for (city in citiesList)
            tabLayout.getTabAt(citiesList.indexOf(city))?.text =
                getString(R.string.city_name_country_string, city.name, city.country)
    }

    fun deleteFragment(city: CityItem?) {
        mSectionsPagerAdapter.removeItem(citiesList.indexOf(city))
        mViewModel.removeCity(city)
    }
}
