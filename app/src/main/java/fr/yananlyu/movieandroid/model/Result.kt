package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<Film>
)
/*{


    *//**
     *
     * @return
     * The id
     *//*
    fun getPage(): Int? {
        return page
    }

    *//**
     *
     * @param page
     * The id
     *//*
    fun setPage(page: Int?) {
        this.page = page
    }

    *//**
     *
     * @return
     * The username
     *//*
    fun getResults(): ArrayList<Film> {
        var films = arrayListOf<Film>()
        return films
    }

    *//**
     *
     * @param results
     * The Username
     *//*
    fun setResults(results: ArrayList<Film>?) {
        this.results = results
    }*/
// }