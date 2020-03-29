package fr.yananlyu.movieandroid

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film
import fr.yananlyu.movieandroid.model.MovieImage
import fr.yananlyu.movieandroid.model.ResultsImages
import kotlinx.android.synthetic.main.movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.android.material.snackbar.Snackbar


class MovieDetailActivity : AppCompatActivity() {
    lateinit var adapter: SlideAdapter
    lateinit var film: Film
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail)
        val id:Int = this.intent.extras!!.getInt("id")
        val backimg = this.intent.extras!!.getString("backdrop_path")
        fetchMovieData(id)
        // send slideShowFragment the id of the movie in order to get the images
        fetchSlideImages(id, this, backimg)
    }
    fun onClick(view: View): Unit {
        val gson: Gson = Gson()
        val sharedPref = this.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("favoris", "[]")
        val typeListFilm = object : TypeToken<ArrayList<Film>>() {}.type
        println("json" + json)
        val films: ArrayList<Film> = gson.fromJson(json, typeListFilm)
        films.add(film)
        with (sharedPref.edit()) {

            val filmJson = gson.toJson(films)
            putString("favoris", filmJson)
            commit()
        }
        Snackbar.make(this.findViewById(android.R.id.content), R.string.favoris_success, Snackbar.LENGTH_SHORT).show();

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
                        adapter = SlideAdapter(context, filterImages(imagesResult))
                    }
                    else if (!backimg.isNullOrEmpty()){
                        val array: ArrayList<MovieImage> = ArrayList()
                        val movieImage: MovieImage = MovieImage(Float.NaN, "/" + backimg!!,0, 0)
                        array.add(movieImage)
                        adapter = SlideAdapter(context, array)
                    } else {
                        adapter = SlideAdapter(context, ArrayList())
                    }
                    viewPager.adapter = adapter
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
    private fun fetchMovieData(id: Int) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.getFilm(id).enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        film = collection
                        displayData(collection)
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
        sb.append("Rating: ").append(film.vote_average)
        numRating.text = sb.toString()
        rating.rating = film.vote_average; // to set rating value
        if(film.poster_path.isNullOrEmpty()){
            poster.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.poster_path)
                .into(poster);
        }

    }
}
