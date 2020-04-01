package fr.yananlyu.movieandroid

import EndlessRecyclerViewScrollListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.yananlyu.movieandroid.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity() {
    lateinit var adapter: SearchResultsAdapter
    lateinit var query: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        // set recyclerView

        val recyclerView = findViewById(R.id.recyclerViewSearch) as RecyclerView // Add this
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.setHasFixedSize(true)
        adapter = SearchResultsAdapter(ArrayList()) { film ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("id", film.id)
            startActivity(intent)
        }
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loadMore(page + 1)
            }
        }
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.adapter = adapter

        // set searchView
        val searchView: SearchView = findViewById(R.id.search_bar)
        searchView.onActionViewExpanded()
        searchView.setIconified(false);
        searchView.queryHint = "Search a movie..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0!!.isNotEmpty()) {
                    query = p0.toLowerCase()
                    searchQuery(query)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // handleIntent(intent!!)
    }
    fun searchQuery(query: String) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.searchMovie(query, 1).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        adapter.clearList()
                        adapter.addList(collection.results)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<Result>, t: Throwable) { //Manage errors
            }
        })
    }
    fun loadMore(page: Int) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.searchMovie(query, page).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        adapter.addList(collection.results)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<Result>, t: Throwable) { //Manage errors
            }
        })
    }
}
