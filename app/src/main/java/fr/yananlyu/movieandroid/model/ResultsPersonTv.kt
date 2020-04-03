package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

class ResultsPersonTv (
    @SerializedName("id")
    var id: Int,
    @SerializedName("MoviePerson")
    var MoviePerson: ArrayList<Tv>
)