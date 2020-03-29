package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class Film(

    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("id")
    val id: Int,
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
    val vote_average: Float

    )