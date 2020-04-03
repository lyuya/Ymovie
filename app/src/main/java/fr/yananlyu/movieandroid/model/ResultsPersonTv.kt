package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

class ResultsPersonTv (
    @SerializedName("id")
    var id: Int,
    @SerializedName("cast")
    var cast: ArrayList<Tv>
)