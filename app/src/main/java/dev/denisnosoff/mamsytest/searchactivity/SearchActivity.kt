package dev.denisnosoff.mamsytest.searchactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.util.hide
import dev.denisnosoff.mamsytest.util.show
import dev.denisnosoff.mamsytest.util.state.Statable
import dev.denisnosoff.mamsytest.util.state.State
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, Statable {

    override var state: State = State.LOADING
        set(_state) {
            field = _state
            changeUI(field)
        }

    override fun changeUI(state: State) {
        when (state) {
            State.SUCCESSFUL -> {
                tvSearchError.hide()
                rvCities.show()
            }
            State.ERROR -> {
                tvSearchError.show()
                rvCities.hide()
            }
        }
    }

    private lateinit var mViewModel: SearchViewModel
    private lateinit var _adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
        mViewModel.citiesLiveData.observe(this, Observer {
            refreshRecyclerView(it)
        })
        mViewModel.stateLiveData.observe(this, Observer {
            state = it
        })
        mViewModel.errorLiveData.observe(this, Observer {
            tvSearchError.text = it
        })
    }

    private fun initRecyclerView() {
        _adapter = RVAdapter(ArrayList()) {
            returnItem(it)
        }
        rvCities.layoutManager = LinearLayoutManager(this)
        rvCities.adapter = _adapter
    }

    private fun returnItem(newItem: CityItem) {
        val intent = Intent()
        intent.putExtra(NEW_CITY_ITEM, newItem)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun refreshRecyclerView(list: List<CityItem>?) {
        list?.let { _adapter.refreshItems(list) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { mViewModel.searchCity(query) }
        return false
    }

    override fun onPause() {
        mViewModel.onPause()
        super.onPause()
    }

    companion object {

        const val SEARCH_ACTIVITY_REQUEST_CODE = 0
        const val NEW_CITY_ITEM = "NEW_CITY_ITEM"

        fun startForResult(requestCode: Int, activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
