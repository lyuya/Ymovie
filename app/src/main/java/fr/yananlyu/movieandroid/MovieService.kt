package fr.yananlyu.movieandroid

import fr.yananlyu.movieandroid.model.Film
import fr.yananlyu.movieandroid.model.Result
import fr.yananlyu.movieandroid.model.ResultsImages
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

interface MovieService {
    @GET("movie/popular")
    fun getPopularFilms(): Call<Result>

    @GET("movie/upcoming")
    fun getUpcomingFilms(): Call<Result>

    @GET("movie/top_rated")
    fun getTopRatedFilms(): Call<Result>

    @GET("movie/now_playing")
    fun getNowPlayingFilms(): Call<Result>

    @GET("movie/{id}")
    fun getFilm(@Path(value = "id", encoded = true) id: Int): Call<Film>

    @GET("movie/{id}/images")
    fun getImages(@Path(value = "id", encoded = true) id: Int): Call<ResultsImages>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String): Call<Result>
}

