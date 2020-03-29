package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class ResultVideos (
    @SerializedName("id")
    var id: Int,
    @SerializedName("results")
    var results: ArrayList<MovieVideo>
)