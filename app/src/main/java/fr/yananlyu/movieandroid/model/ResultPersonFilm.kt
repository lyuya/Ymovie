package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class ResultPersonFilm (
    @SerializedName("id")
    var id: Int,
    @SerializedName("cast")
    var cast: ArrayList<Film>
)