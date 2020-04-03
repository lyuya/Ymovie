package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class ResultsSearch (
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<Item>
)