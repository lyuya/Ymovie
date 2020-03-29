package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("name")
    var name: String,
    @SerializedName("profile_path")
    var profile_path: String?
)

