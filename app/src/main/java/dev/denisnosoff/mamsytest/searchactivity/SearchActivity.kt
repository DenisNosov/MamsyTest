package dev.denisnosoff.mamsytest.searchactivity

import android.animation.LayoutTransition
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import dev.denisnosoff.mamsytest.R

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val TAG = "SearchActivity"

    private lateinit var mViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mViewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
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
