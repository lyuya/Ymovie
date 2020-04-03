package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

class Season (
    @SerializedName("air_date")
    val air_date: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val poster_path: String
)
