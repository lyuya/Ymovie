package fr.yananlyu.movieandroid.ui.favoris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.yananlyu.movieandroid.*
import fr.yananlyu.movieandroid.model.Film
import kotlinx.android.synthetic.main.fragment_favoris.*

class FavorisFragment : Fragment() {

    lateinit var adapter: FavorisAdapter
    lateinit var films: ArrayList<Film>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val listItemBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.list_item, container, false)

        val root = inflater.inflate(R.layout.fragment_favoris, container, false)

        val recyclerView = root.findViewById(R.id.recyclerViewFavoris) as RecyclerView // Add this
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)

        adapter = FavorisAdapter(ArrayList()) { film ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("backdrop_path", film.backdrop_path)
            startActivityForResult(intent, 1)
        }
        recyclerView.adapter = adapter
        fetchData()
        if (films.isEmpty()) {
            val textView:TextView = root.findViewById(R.id.text_favoris)
            textView.setText(R.string.favoris_empty)
            textView.setPadding(5,5, 5,5)
            textView.gravity = Gravity.CENTER
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fetchData()
    }

    private fun fetchData() {
        val gson: Gson = Gson()
        val sharedPref = this.activity?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("favoris", "[]")
        val typeListFilm = object : TypeToken<ArrayList<Film>>() {}.type
        films = gson.fromJson(json, typeListFilm)
        adapter.addList(films)
    }
}