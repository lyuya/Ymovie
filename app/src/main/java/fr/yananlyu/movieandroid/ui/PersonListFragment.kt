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
import fr.yananlyu.movieandroid.activity.PersonDetailActivity
import fr.yananlyu.movieandroid.adapter.ActorsAdapter
import fr.yananlyu.movieandroid.model.ResultPerson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PersonListFragment : Fragment() {
    lateinit var adapter: ActorsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_person, container, false)
        val recyclerView = root.findViewById(R.id.recyclerViewPerson) as RecyclerView // Add this
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = ActorsAdapter(ArrayList()) { person ->
            val intent = Intent(context, PersonDetailActivity::class.java)
            intent.putExtra("person_id", person.id)
            startActivity(intent)
        }
        val scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                fetchData(page + 1)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.adapter = adapter

        fetchData(1)
        return root
    }
    private fun fetchData(page: Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getPersonPopular(page).enqueue(object : Callback<ResultPerson> {
            override fun onResponse(call: Call<ResultPerson>, response: Response<ResultPerson>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        adapter.addList(collection.results)
                    } else {
                        Toast.makeText(context, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultPerson>, t: Throwable) {
                Toast.makeText(context, getString(R.string.app_error), Toast.LENGTH_LONG).show()
            }
        })
    }

}
