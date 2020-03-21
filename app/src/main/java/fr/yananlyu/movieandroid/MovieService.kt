package fr.yananlyu.movieandroid

import fr.yananlyu.movieandroid.model.Film
import fr.yananlyu.movieandroid.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList

interface MovieService {
    @GET("movie/popular")
    abstract fun getPopularFilms(): Call<Result>

    @GET("movie/upcoming")
    abstract fun getUpcomingFilms(): Call<ArrayList<Film>>

    @GET("movie/top_rated")
    abstract fun getTopRatedFilms(): Call<ArrayList<Film>>

    @GET("movie/now_playing")
    abstract fun getNowPlayingFilms(): Call<ArrayList<Film>>
}

