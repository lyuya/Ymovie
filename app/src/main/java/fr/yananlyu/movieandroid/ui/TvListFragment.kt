package fr.yananlyu.movieandroid.ui

import EndlessRecyclerViewScrollListener
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.yananlyu.movieandroid.MovieService

import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.RetrofitInstance
import fr.yananlyu.movieandroid.activity.MovieDetailActivity
import fr.yananlyu.movieandroid.activity.TvDetailActivity
import fr.yananlyu.movieandroid.adapter.RecyclerViewAdapter
import fr.yananlyu.movieandroid.adapter.TvAdapter
import fr.yananlyu.movieandroid.model.Film
import fr.yananlyu.movieandroid.model.ResultsTv
import fr.yananlyu.movieandroid.model.Tv
import kotlinx.android.synthetic.main.fragment_tv_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvListFragment : Fragment() {
    lateinit var adapter: TvAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_tv_list, container, false)
        val recyclerView = root.findViewById(R.id.recyclerViewTv) as RecyclerView // Add this
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = TvAdapter(ArrayList()) { tv ->
            val intent = Intent(context, TvDetailActivity::class.java)
            intent.putExtra("id", tv.id)
            startActivity(intent)
        }
        val scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                fetchData(page + 1)
            }
        }
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.adapter = adapter

        fetchData(1)
        return root
    }
    private fun fetchData(page: Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getTvPopular(page).enqueue(object : Callback<ResultsTv> {
            override fun onResponse(call: Call<ResultsTv>, response: Response<ResultsTv>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        adapter.addList(collection.results)
                    } else {
                        Toast.makeText(context, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultsTv>, t: Throwable) { //Manage errors
            }
        })
    }

}
