package fr.yananlyu.movieandroid.activity

import EndlessRecyclerViewScrollListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.yananlyu.movieandroid.MovieService
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.RetrofitInstance
import fr.yananlyu.movieandroid.adapter.SearchResultsAdapter
import fr.yananlyu.movieandroid.model.ResultsSearch
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
        adapter = SearchResultsAdapter(ArrayList()) { item ->
            if (item.media_type == "movie") {
                val intent = Intent(this, MovieDetailActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            } else if (item.media_type == "person") {
                val intent = Intent(this, PersonDetailActivity::class.java)
                intent.putExtra("person_id", item.id)
                startActivity(intent)
            } else if(item.media_type == "tv") {
                val intent = Intent(this, TvDetailActivity::class.java)
                intent.putExtra("tv_id", item.id)
                startActivity(intent)
            }

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
        searchView.queryHint = "Search movie/TV/artist..."
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
        val textView: TextView = findViewById(R.id.empty_result_search)
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.searchItem(query, 1).enqueue(object : Callback<ResultsSearch> {
            override fun onResponse(call: Call<ResultsSearch>, response: Response<ResultsSearch>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        collection.results.filter { it.media_type == "movie" || it.media_type == "person"}
                        if(!collection.results.isNullOrEmpty()) {
                            textView.visibility = View.INVISIBLE
                            adapter.clearList()
                            adapter.addList(collection.results)
                        } else {
                            adapter.clearList()
                            adapter.addList(collection.results)
                            textView.visibility = View.VISIBLE
                            textView.setText(R.string.empty_search_results)
                            textView.gravity = Gravity.CENTER
                        }
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResultsSearch>, t: Throwable) { //Manage errors
                println(t)
            }
        })
    }
    fun loadMore(page: Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.searchItem(query, page).enqueue(object : Callback<ResultsSearch> {
            override fun onResponse(call: Call<ResultsSearch>, response: Response<ResultsSearch>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        adapter.addList(collection.results)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResultsSearch>, t: Throwable) { //Manage errors
            }
        })
    }
}
