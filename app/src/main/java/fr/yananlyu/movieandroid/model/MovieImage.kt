package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.SerializedName

class MovieImage (
    @SerializedName("aspect_ratio")
    val aspect_ratio: Float,
    @SerializedName("file_path")
    val file_path: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)