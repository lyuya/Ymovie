package fr.yananlyu.movieandroid.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.yananlyu.movieandroid.*
import fr.yananlyu.movieandroid.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListMovieFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListMovieFragment : Fragment() {

    lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView // Add this
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)

        adapter = RecyclerViewAdapter(ArrayList()) { feature ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchData()
        return root
    }


    private fun fetchData() {
        val movieDBService = RetrofitInstance.getInstance().create(MovieService::class.java)
        when(this.arguments?.get("numFragment")) {
            0 -> {
                movieDBService.getPopularFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addFeatureList(results)
                            println("size" + collection.results.size)
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
                movieDBService.getUpcomingFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addFeatureList(results)
                            println("size" + collection.results.size)
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
                movieDBService.getTopRatedFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addFeatureList(results)
                            println("size" + collection.results.size)
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
                movieDBService.getNowPlayingFilms().enqueue(object : Callback<Result> {

                    override fun onResponse(
                        call: Call<Result>,
                        response: Response<Result>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val collection: Result = response.body()
                            val results = collection.results
                            adapter.addFeatureList(results)
                            println("size" + collection.results.size)
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
