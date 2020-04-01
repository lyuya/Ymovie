package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

data class MoviePerson (
    @SerializedName("id")
    val id: Int,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("deathday")
    val deathday: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("profile_path")
    val profile_path: String,
    @SerializedName("place_of_birth")
    val place_of_birth: String

)