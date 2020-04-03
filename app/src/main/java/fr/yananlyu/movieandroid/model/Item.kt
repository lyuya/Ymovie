package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name")
    var name: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("profile_path")
    var profile_path: String?,
    @SerializedName("media_type")
    val media_type: String,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("vote_average")
    val vote_average: Float,
    @SerializedName("number_of_episodes")
    val number_of_episodes: Int,
    @SerializedName("number_of_seasons")
    val number_of_seasons: Int,
    @SerializedName("original_name")
    val original_name: String,
    @SerializedName("first_air_date")
    val first_air_date: String
)