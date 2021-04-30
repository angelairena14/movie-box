package com.test.moviebox.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<MovieListDetail?>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)

data class MovieListDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("title")
    val title: String
){
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as MovieListDetail
        if (id != other.id) return false
        if (poster_path != other.poster_path) return false
        if (release_date != other.release_date) return false
        if (title != other.title) return false
        return true
    }
}