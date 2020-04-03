package fr.yananlyu.movieandroid

import fr.yananlyu.movieandroid.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("search/multi")
    fun searchItem(@Query("query") query: String, @Query("page") page: Int?): Call<ResultsSearch>

    @GET("movie/{id}/credits")
    fun getCredits(@Path(value = "id", encoded = true) id: Int): Call<ResultMoviePerson>

    @GET("movie/{id}/similar")
    fun getSimilarFilms(@Path(value = "id", encoded = true) id: Int): Call<Result>

    @GET("movie/{id}/videos")
    fun getVideos(@Path(value = "id", encoded = true) id: Int): Call<ResultVideos>

    @GET("person/{person_id}")
    fun getPerson(@Path(value = "person_id", encoded = true) person_id: Int): Call<MoviePerson>

    @GET("person/{person_id}/movie_credits")
    fun getPersonMovies(@Path(value = "person_id", encoded = true) person_id: Int): Call<ResultPersonFilm>

    @GET("person/popular")
    fun getPersonPopular(@Query("page") page: Int?): Call<ResultPerson>

    @GET("tv/popular")
    fun getTvPopular(@Query("page") page: Int?): Call<ResultsTv>

    @GET("tv/{id}")
    fun getTv(@Path(value = "id", encoded = true) id: Int): Call<Tv>

    @GET("tv/{id}/credits")
    fun getTvCredits(@Path(value = "id", encoded = true) id: Int): Call<ResultMoviePerson>

    @GET("person/{person_id}/tv_credits")
    fun getPersonTv(@Path(value = "person_id", encoded = true) id: Int): Call<ResultsPersonTv>
}

