package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

class Tv(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("number_of_episodes")
    val number_of_episodes: Int,
    @SerializedName("number_of_seasons")
    val number_of_seasons: Int,
    @SerializedName("original_name")
    val original_name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("seasons")
    val seasons: ArrayList<Season>,
    @SerializedName("first_air_date")
    val first_air_date: String,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("vote_average")
    val vote_average: Float
)