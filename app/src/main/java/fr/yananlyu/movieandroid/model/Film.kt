package fr.yananlyu.movieandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Film() {

    @SerializedName("adult")
    @Expose
    private val adult: Boolean = false
    @SerializedName("backdrop_path")
    @Expose
    private val backdrop_path: String? = null
    @SerializedName("id")
    @Expose
    private val id: Int = 0
    @SerializedName("original_title")
    @Expose
    private val original_title: String? = null
    @SerializedName("overview")
    @Expose
    private val overview: String? = null
    @SerializedName("popularity")
    @Expose
    private val popularity: Double = 0.00
    @SerializedName("poster_path")
    @Expose
    private val poster_path: String? = null
    @SerializedName("original_language")
    @Expose
    private val original_language: String? = null

    override fun toString(): String {
        return "Film(adult=$adult, backdrop_path=$backdrop_path, id=$id, original_language=$original_language, original_title=$original_title, overview=$overview, popularity=$popularity, poster_path=$poster_path)"
    }
    /**
     *
     * @return
     * adult
     */
    fun getAdult(): Boolean? {
        return adult
    }

    /**
     *
     * @return
     * backdrop_path
     */
    fun getBackdrop_path(): String? {
        return backdrop_path
    }

    /**
     *
     * @return
     * The id
     */
    fun getId(): Int? {
        return id
    }

    /**
     *
     * @return
     * original_title
     */
    fun getOriginal_title(): String? {
        return original_title
    }

    /**
     *
     * @return
     * overview
     */
    fun getOverview(): String? {
        return overview
    }

    /**
     *
     * @return
     * popularity
     */
    fun getPopularity(): Double? {
        return popularity
    }

    /**
     *
     * @return
     * poster_path
     */
    fun getPoster_path(): String? {
        return poster_path
    }

    /**
     *
     * @return
     * poster_path
     */
    fun getOriginal_language(): String? {
        return original_language
    }
}