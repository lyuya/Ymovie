package fr.yananlyu.movieandroid.ui.favoris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.yananlyu.movieandroid.*
import fr.yananlyu.movieandroid.model.Film

class FavorisFragment : Fragment() {

    lateinit var adapter: FavorisAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favoris, container, false)

        val recyclerView = root.findViewById(R.id.recyclerViewFavoris) as RecyclerView // Add this
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)

        adapter = FavorisAdapter(ArrayList()) { film ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("backdrop_path", film.backdrop_path)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        fetchData()
        return root
    }

    private fun fetchData() {
        val gson: Gson = Gson()
        val sharedPref = this.activity?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("favoris", "[]")
        val typeListFilm = object : TypeToken<ArrayList<Film>>() {}.type
        val films: ArrayList<Film> = gson.fromJson(json, typeListFilm)
        println("in favoris" + json)
        println("size" + films.size)
        adapter.addList(films)
        // val service = RetrofitInstance.getInstance().create(MovieService::class.java)
    }
}