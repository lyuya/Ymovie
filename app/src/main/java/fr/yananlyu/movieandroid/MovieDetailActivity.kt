package fr.yananlyu.movieandroid

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film
import kotlinx.android.synthetic.main.movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail)

/*        agencyDetailToolbar.title = getString(R.string.agence_label)
        setSupportActionBar(agencyDetailToolbar)

        agencyDetailToolbar.setNavigationIcon(R.drawable.ic_back)
        agencyDetailToolbar.setNavigationOnClickListener {
            finish()
        }*/
        val id:Int = this.intent.extras!!.getInt("id")
        println("id: " + id)
        fetchMovieData(id)
        /*val name = intent.getStringExtra(MainActivity.FEATURE_NAME)
        name?.let {

        }*/
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

        Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.backdrop_path)
            .into(backimg);
        Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.poster_path)
            .into(poster);
    }
}
