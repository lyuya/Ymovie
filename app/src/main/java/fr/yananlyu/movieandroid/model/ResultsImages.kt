package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class ResultsImages (
    @SerializedName("id")
    var id: Int,
    @SerializedName("backdrops")
    var backdrops: ArrayList<MovieImage>
)