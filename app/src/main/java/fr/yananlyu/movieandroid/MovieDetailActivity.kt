package fr.yananlyu.movieandroid

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
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



class MovieDetailActivity : AppCompatActivity() {
    lateinit var adapter: SlideAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail)
        val id:Int = this.intent.extras!!.getInt("id")
        val backimg = this.intent.extras!!.getString("backdrop_path")
        fetchMovieData(id)
        // send slideShowFragment the id of the movie in order to get the images
        fetchSlideImages(id, this, backimg)
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
                    else if (backimg!=null){
                        val array: ArrayList<MovieImage> = ArrayList()
                        println("backimg: " + backimg)
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
        // filter 4 images avec ratio 1.7777
        if (images.size < 4) {
          return images
        } else
            {
            return ArrayList(images.filter { it.aspect_ratio > 1.7 && it.width == 3840})
        }
    }
    private fun fetchMovieData(id: Int) {
        val metroService = RetrofitInstance.getInstance().create(MovieService::class.java)
        metroService.getFilm(id).enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
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

/*        Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.backdrop_path)
            .into(backimg);*/
        Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.poster_path)
            .into(poster);
    }
}
