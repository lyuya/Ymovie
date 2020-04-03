package fr.yananlyu.movieandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.MovieService
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.RetrofitInstance
import fr.yananlyu.movieandroid.adapter.ActorsAdapter
import fr.yananlyu.movieandroid.adapter.SeasonAdapter
import fr.yananlyu.movieandroid.model.ResultMoviePerson
import fr.yananlyu.movieandroid.model.Season
import fr.yananlyu.movieandroid.model.Tv
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.activity_tv_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvDetailActivity : AppCompatActivity() {
    lateinit var tv: Tv
    lateinit var actorsAdapter: ActorsAdapter
    lateinit var seasonAdapter: SeasonAdapter
    lateinit var seasons: ArrayList<Season>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)
        recyclerview_tv_person.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerview_tv_season.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actorsAdapter =
            ActorsAdapter(ArrayList()) { person ->
                val intent =
                    Intent(this, PersonDetailActivity::class.java)
                intent.putExtra("person_id", person.id)
                finish()
                startActivity(intent)
            }
        seasonAdapter = SeasonAdapter(ArrayList()) { season ->
        }
        recyclerview_tv_person.adapter = actorsAdapter
        recyclerview_tv_season.adapter = seasonAdapter
        val id:Int = this.intent.extras!!.getInt("tv_id")
        fetchData(id)
        fetchCredits(id)
    }
    fun fetchData(id:Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getTv(id).enqueue(object : Callback<Tv> {
            override fun onResponse(call: Call<Tv>, response: Response<Tv>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        tv = collection
                        seasons = collection.seasons
                        seasonAdapter.addList(seasons)
                        displayData(tv)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Tv>, t: Throwable) { //Manage errors
            }
        })
    }
    private fun displayData(tv: Tv) {
        name_tv.text = tv.original_name
        resume_tv.text = tv.overview
        date_tv.text = tv.first_air_date

        if(tv.poster_path.isNullOrEmpty()){
            poster_tv.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + tv.poster_path)
                .into(poster_tv)
        }
    }

    private fun fetchCredits(id: Int) {
        val service = RetrofitInstance.getInstance()
            .create(MovieService::class.java)
        service.getTvCredits(id).enqueue(object : Callback<ResultMoviePerson> {
            override fun onResponse(call: Call<ResultMoviePerson>, response: Response<ResultMoviePerson>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        if (!collection.cast.isNullOrEmpty()) {
                            actorsAdapter.addList(collection.cast)
                        } else {
                            val textView: TextView = findViewById(R.id.empty_actors)
                            textView.setText(R.string.empty_actors)
                            textView.gravity = Gravity.CENTER
                        }
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResultMoviePerson>, t: Throwable) { //Manage errors
            }
        })
    }
}
