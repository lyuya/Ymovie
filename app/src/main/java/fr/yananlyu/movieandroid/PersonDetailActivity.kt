package fr.yananlyu.movieandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film
import fr.yananlyu.movieandroid.model.MoviePerson
import fr.yananlyu.movieandroid.model.ResultCast
import fr.yananlyu.movieandroid.model.ResultPerson
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailActivity : AppCompatActivity() {
    lateinit var person: MoviePerson
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        val recyclerViewMoviePerson = findViewById(R.id.recyclerview_person_movies) as RecyclerView
        recyclerViewMoviePerson.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAdapter = RecyclerViewAdapter(ArrayList()) { film ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("backdrop_path", film.backdrop_path)
            finish()
            startActivity(intent)
        }
        recyclerViewMoviePerson.adapter = recyclerViewAdapter
        val id:Int = this.intent.extras!!.getInt("id")
        fetchData(id)
        fetchMovies(id)
    }

    fun fetchData(id:Int) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
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
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.getPersonMovies(id).enqueue(object : Callback<ResultPerson> {
            override fun onResponse(call: Call<ResultPerson>, response: Response<ResultPerson>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        recyclerViewAdapter.addList(collection.cast)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultPerson>, t: Throwable) { //Manage errors
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
        sb.append(person.birthday).append("~").append(deathday)
        birthday_person.text = sb.toString()
        resume_person.text = person.biography
        place_birth.text = person.place_of_birth

        if(person.profile_path.isNullOrEmpty()){
            poster_person.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + person.profile_path)
                .into(poster_person)
        }
    }
}
