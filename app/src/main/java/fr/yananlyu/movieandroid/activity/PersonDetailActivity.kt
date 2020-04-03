package fr.yananlyu.movieandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.MovieService
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.adapter.RecyclerViewAdapter
import fr.yananlyu.movieandroid.RetrofitInstance
import fr.yananlyu.movieandroid.adapter.TvAdapter
import fr.yananlyu.movieandroid.model.MoviePerson
import fr.yananlyu.movieandroid.model.Result
import fr.yananlyu.movieandroid.model.ResultPersonFilm
import fr.yananlyu.movieandroid.model.ResultsPersonTv
import kotlinx.android.synthetic.main.activity_person_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailActivity : AppCompatActivity() {
    lateinit var person: MoviePerson
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var tvAdapter: TvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        val recyclerViewMoviePerson = findViewById(R.id.recyclerview_person_movies) as RecyclerView
        val recyclerViewTvPerson = findViewById(R.id.recyclerview_person_tvs) as RecyclerView
        recyclerViewMoviePerson.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTvPerson.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewAdapter =
            RecyclerViewAdapter(ArrayList()) { film ->
                val intent =
                    Intent(this, MovieDetailActivity::class.java)
                intent.putExtra("id", film.id)
                intent.putExtra("backdrop_path", film.backdrop_path)
                finish()
                startActivity(intent)
            }
        tvAdapter =
            TvAdapter(ArrayList()) { tv ->
                val intent =
                    Intent(this, TvDetailActivity::class.java)
                intent.putExtra("id", tv.id)
                finish()
                startActivity(intent)
            }
        recyclerViewMoviePerson.adapter = recyclerViewAdapter
        recyclerViewTvPerson.adapter = tvAdapter
        val id:Int = this.intent.extras!!.getInt("id")
        fetchData(id)
        fetchMovies(id)
        fetchTvs(id)
    }

    fun fetchData(id:Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getPerson(id).enqueue(object : Callback<MoviePerson> {
            override fun onResponse(call: Call<MoviePerson>, response: Response<MoviePerson>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        person = collection
                        displayData(person)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<MoviePerson>, t: Throwable) { //Manage errors
            }
        })
    }
    fun fetchMovies(id: Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getPersonMovies(id).enqueue(object : Callback<ResultPersonFilm> {
            override fun onResponse(call: Call<ResultPersonFilm>, response: Response<ResultPersonFilm>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        recyclerViewAdapter.addList(collection.cast)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultPersonFilm>, t: Throwable) { //Manage errors
            }
        })
    }
    private fun displayData(person: MoviePerson) {
        name_person.text = person.name
        val sb = StringBuilder()
        var deathday = person.deathday
        if (person.deathday == null) {
            deathday = "Now"
        }
        if (person.birthday != null) {
            sb.append(person.birthday).append("~").append(deathday)
            birthday_person.text = sb.toString()
        } else {
            birthday_person.visibility = View.INVISIBLE
        }
        resume_person.text = person.biography
        place_birth.text = person.place_of_birth

        if(person.profile_path.isNullOrEmpty()){
            poster_person.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + person.profile_path)
                .into(poster_person)
        }
    }

    private fun fetchTvs(id: Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getPersonTv(id).enqueue(object : Callback<ResultsPersonTv> {
            override fun onResponse(call: Call<ResultsPersonTv>, response: Response<ResultsPersonTv>) {
                if (response.isSuccessful && response.body() != null) {
                    val collection = response.body()
                    if(collection != null) {
                        if(!collection.cast.isNullOrEmpty()) {
                            tvAdapter.addList(collection.cast)
                        } else {
                            val textView: TextView = findViewById(R.id.empty_movies)
                            textView.setText(R.string.empty_movies)
                            textView.gravity = Gravity.CENTER
                        }
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultsPersonTv>, t: Throwable) {
            }
        })
    }
}
