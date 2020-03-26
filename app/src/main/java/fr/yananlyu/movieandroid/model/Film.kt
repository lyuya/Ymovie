package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Film(

    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("vote_average")
    val vote_average: Float

    )
    /*override fun toString(): String {
        return "Film(adult=$adult, backdrop_path=$backdrop_path, id=$id, original_language=$original_language, original_title=$original_title, overview=$overview, popularity=$popularity, poster_path=$poster_path)"
    }
    *//**
     *
     * @return
     * adult
     *//*
    fun getAdult(): Boolean? {
        return adult
    }

    *//**
     *
     * @return
     * backdrop_path
     *//*
    fun getBackdrop_path(): String? {
        return backdrop_path
    }

    *//**
     *
     * @return
     * The id
     *//*
    fun getId(): Int? {
        return id
    }

    *//**
     *
     * @return
     * original_title
     *//*
    fun getOriginal_title(): String? {
        return original_title
    }

    *//**
     *
     * @return
     * overview
     *//*
    fun getOverview(): String? {
        return overview
    }

    *//**
     *
     * @return
     * popularity
     *//*
    fun getPopularity(): Double? {
        return popularity
    }

    *//**
     *
     * @return
     * poster_path
     *//*
    fun getPoster_path(): String? {
        return poster_path
    }

    *//**
     *
     * @return
     * poster_path
     *//*
    fun getOriginal_language(): String? {
        return original_language
    }*/
