package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

class ResultsTv (
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<Tv>
    )