package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {
    @SerializedName("page")
    @Expose
    private var page: Int? = null
    @SerializedName("results")
    @Expose
    private var results: ArrayList<Film>? = null

    /**
     *
     * @return
     * The id
     */
    fun getPage(): Int? {
        return page
    }

    /**
     *
     * @param page
     * The id
     */
    fun setPage(page: Int?) {
        this.page = page
    }

    /**
     *
     * @return
     * The username
     */
    fun getResults(): ArrayList<Film>? {
        return results
    }

    /**
     *
     * @param results
     * The Username
     */
    fun setResults(results: ArrayList<Film>?) {
        this.results = results
    }
}