package fr.yananlyu.movieandroid

import fr.yananlyu.movieandroid.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

interface MovieService {
    @GET("movie/popular")
    fun getPopularFilms(@Query("page") page: Int?): Call<Result>

    @GET("movie/upcoming")
    fun getUpcomingFilms(@Query("page") page: Int?): Call<Result>

    @GET("movie/top_rated")
    fun getTopRatedFilms(@Query("page") page: Int?): Call<Result>

    @GET("movie/now_playing")
    fun getNowPlayingFilms(@Query("page") page: Int?): Call<Result>

    @GET("movie/{id}")
    fun getFilm(@Path(value = "id", encoded = true) id: Int): Call<Film>

    @GET("movie/{id}/images")
    fun getImages(@Path(value = "id", encoded = true) id: Int): Call<ResultsImages>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int?): Call<Result>

    @GET("movie/{id}/credits")
    fun getCredits(@Path(value = "id", encoded = true) id: Int): Call<ResultCast>

    @GET("movie/{id}/similar")
    fun getSimilarFilms(@Path(value = "id", encoded = true) id: Int): Call<Result>

    @GET("movie/{id}/videos")
    fun getVideos(@Path(value = "id", encoded = true) id: Int): Call<ResultVideos>

    @GET("person/{person_id}")
    fun getPerson(@Path(value = "person_id", encoded = true) person_id: Int): Call<MoviePerson>

    @GET("person/{person_id}/movie_credits")
    fun getPersonMovies(@Path(value = "person_id", encoded = true) person_id: Int): Call<ResultPerson>

}

