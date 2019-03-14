package dev.denisnosoff.mamsytest.searchactivity

import android.animation.LayoutTransition
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.model.cities.CityItem
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val TAG = "SearchActivity"

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
    }

    private fun initRecyclerView() {
        _adapter = RVAdapter(ArrayList()) {
            Toast.makeText(this, "${it.name}. ${it.id}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = _adapter
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
        query?.let {
            Log.d(TAG, "querying city $query")
            mViewModel.searchCity(query) }
        return false
    }

    override fun onPause() {
        mViewModel.onPause()
        super.onPause()
    }
}
