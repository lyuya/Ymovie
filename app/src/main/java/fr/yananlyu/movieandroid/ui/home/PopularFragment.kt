package fr.yananlyu.movieandroid.ui.home

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
import fr.yananlyu.movieandroid.*
import fr.yananlyu.movieandroid.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PopularFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PopularFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopularFragment : Fragment() {

    lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_popular, container, false)
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
}
