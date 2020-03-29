package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<Film>
)