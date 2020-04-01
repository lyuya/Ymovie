package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class ResultPerson (
    @SerializedName("id")
    var id: Int,
    @SerializedName("cast")
    var cast: ArrayList<Film>
)