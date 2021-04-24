package com.test.moviebox.model

data class ErrorResponseModel(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)