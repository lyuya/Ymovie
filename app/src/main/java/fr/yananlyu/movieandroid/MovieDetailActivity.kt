package fr.yananlyu.movieandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.android.material.snackbar.Snackbar
import fr.yananlyu.movieandroid.model.*
import fr.yananlyu.movieandroid.ui.favoris.FavorisFragment


class MovieDetailActivity : AppCompatActivity() {
    lateinit var slideAdapter: SlideAdapter
    lateinit var film: Film
    lateinit var actorsAdapter: ActorsAdapter
    lateinit var similarFilmsAdapter: SimilarFilmsAdapter

    val gson: Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail)
        val recyclerViewActors = findViewById(R.id.recyclerviewActors) as RecyclerView
        val recyclerViewSimiars = findViewById(R.id.recyclerviewSimilars) as RecyclerView
        recyclerViewActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actorsAdapter = ActorsAdapter(ArrayList()) { cast ->
            val intent = Intent(this, PersonDetailActivity::class.java)
            intent.putExtra("id", cast.id)
            print("cast.id" + cast.id)
            finish()
            startActivity(intent)
        }

        recyclerViewSimiars.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarFilmsAdapter = SimilarFilmsAdapter(ArrayList()) { film ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("backdrop_path", film.backdrop_path)
            finish()
            startActivity(intent)
        }

        recyclerViewActors.adapter = actorsAdapter
        recyclerViewSimiars.adapter = similarFilmsAdapter

        val id:Int = this.intent.extras!!.getInt("id")
        val backimg = this.intent.extras!!.getString("backdrop_path")
        fetchMovieData(id)
        // send slideShowFragment the id of the movie in order to get the images
        fetchSlideImages(id, this, backimg)
        fetchCredits(id)
        fetchSilmilarFilms(id)
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());

    }
    fun onClick(view: View): Unit {

            val sharedPref = this.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
            val films = getFilmsFromSharePreferences()

        if (isFavoris(film)) {
            var filmToRemove:Film?=null
            films.forEach{
                if (it.id==film.id) {
                    filmToRemove = it
                }
            }
            films.remove(filmToRemove)
            with(sharedPref.edit()) {

                val filmJson = gson.toJson(films)
                putString("favoris", filmJson)
                commit()
            }
            Snackbar.make(
                this.findViewById(android.R.id.content),
                R.string.favoris_remove,
                Snackbar.LENGTH_SHORT
            ).show();
            showIconFavoris()
        } else {
            films.add(film)
            with(sharedPref.edit()) {

                val filmJson = gson.toJson(films)
                putString("favoris", filmJson)
                commit()
            }
            Snackbar.make(
                this.findViewById(android.R.id.content),
                R.string.favoris_success,
                Snackbar.LENGTH_SHORT
            ).show();
            showIconFavoris()
        }
    }
    private fun getListener(): FragmentManager.OnBackStackChangedListener {
        return object : FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                val manager = supportFragmentManager
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                val currentFragment: Fragment? = navHostFragment?.getChildFragmentManager()?.getFragments()?.get(0);
                if (FavorisFragment::class.isInstance(currentFragment))
                    currentFragment?.onResume()
            }
        }
    }
    private fun fetchSlideImages(id: Int, context: Context, backimg: String? = null) {
        val movieDBService = RetrofitInstance.getInstance().create(MovieService::class.java)
        movieDBService.getImages(id).enqueue(object : Callback<ResultsImages> {

            override fun onResponse(
                call: Call<ResultsImages>,
                response: Response<ResultsImages>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val collection: ResultsImages = response.body()
                    val imagesResult = collection.backdrops
                    val viewPager: ViewPager = findViewById(R.id.viewPagerSlide)
                    if (filterImages(imagesResult).size != 0) {
                        slideAdapter = SlideAdapter(context, filterImages(imagesResult))
                    }
                    else if (!backimg.isNullOrEmpty()){
                        val array: ArrayList<MovieImage> = ArrayList()
                        val movieImage: MovieImage = MovieImage(Float.NaN, "/" + backimg!!,0, 0)
                        array.add(movieImage)
                        slideAdapter = SlideAdapter(context, array)
                    } else {
                        slideAdapter = SlideAdapter(context, ArrayList())
                    }
                    viewPager.adapter = slideAdapter
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.app_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onFailure(call: Call<ResultsImages>, t: Throwable) {
                println(t)
            }
        })
    }
    private fun filterImages(images: ArrayList<MovieImage>): ArrayList<MovieImage> {
        // filter les images avec ratio 1.7777
        return ArrayList(images.filter { it.aspect_ratio > 1.7 })
    }

    private fun fetchCredits(id: Int) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.getCredits(id).enqueue(object : Callback<ResultCast> {
            override fun onResponse(call: Call<ResultCast>, response: Response<ResultCast>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        actorsAdapter.addList(collection.cast)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultCast>, t: Throwable) { //Manage errors
            }
        })
    }
    private fun fetchSilmilarFilms(id: Int) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.getSimilarFilms(id).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful && response.body() != null) {
                    val collection = response.body()
                    if(collection != null) {
                        similarFilmsAdapter.addList(collection.results)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
            }
        })
    }
    private fun fetchMovieData(id: Int) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.getFilm(id).enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        film = collection
                        displayData(film)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) { //Manage errors
            }
        })
    }

    private fun displayData(film: Film) {
        titre.text = film.original_title
        annee.text = film.release_date
        resume.text = film.overview
        val sb = StringBuilder()
        sb.append(film.vote_average).append("/10")
        numRating.text = sb.toString()
        rating.rating = film.vote_average; // to set rating value
        showIconFavoris()
        if(film.poster_path.isNullOrEmpty()){
            poster.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.poster_path)
                .into(poster);
        }
    }
    fun showIconFavoris() {
        val button = findViewById<MaterialButton>(R.id.buttonFavoris)
        if (isFavoris(film)) {
            button.setIconResource(R.drawable.ic_favorite_black_24dp)
        } else {
            button.setIconResource(R.drawable.ic_favorite_border_black_24dp)
        }
    }
    fun getFilmsFromSharePreferences(): ArrayList<Film> {
        val sharedPref = this.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val json = sharedPref.getString("favoris", "[]")
        val typeListFilm = object : TypeToken<ArrayList<Film>>() {}.type
        val films: ArrayList<Film> = gson.fromJson(json, typeListFilm)
        return films
    }
    private fun isFavoris(film: Film): Boolean {
        var isFavoris: Boolean = false
        getFilmsFromSharePreferences().forEach{
            if (it.id == film.id) {
                isFavoris = true
            }
        }
        return isFavoris
    }
}
