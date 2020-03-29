package fr.yananlyu.movieandroid.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.yananlyu.movieandroid.*
import fr.yananlyu.movieandroid.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMovieFragment : Fragment() {

    lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView // Add this
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)

        adapter = RecyclerViewAdapter(ArrayList()) { film ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("backdrop_path", film.backdrop_path)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchData()
        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun fetchData() {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        if(this.arguments?.get("query")!=null) {
            service.searchMovie(this.arguments?.getString("query")!!).enqueue(object : Callback<Result> {

                override fun onResponse(
                    call: Call<Result>,
                    response: Response<Result>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val collection: Result = response.body()
                        val results = collection.results
                        adapter.addList(results)
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.app_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: Call<Result>, t: Throwable) {
                    println(t)
                }
            })
        }
        when(this.arguments?.get("numFragment")) {
            0 -> {
                service.getPopularFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addList(results)
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.app_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        println(t)
                    }
                })
            }
            1 -> {
                service.getUpcomingFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addList(results)
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.app_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        println(t)
                    }
                })
            }
            2 -> {
                service.getTopRatedFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addList(results)
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.app_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        println(t)
                    }
                })
            }
            3 -> {
                service.getNowPlayingFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addList(results)
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.app_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        println(t)
                    }
                })
            }
        }
    }
}
