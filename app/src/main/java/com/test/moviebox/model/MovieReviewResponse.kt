package com.test.moviebox.model

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ReviewResults>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)

data class ReviewResults(
    @SerializedName("author")
    val author: String,
    @SerializedName("author_details")
    val author_details: AuthorDetails,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("url")
    val url: String
)

data class AuthorDetails(
    @SerializedName("avatar_path")
    val avatar_path: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("username")
    val username: String
)