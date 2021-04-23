package com.test.moviebox.utils

sealed class NewResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NewResource<T>(data)
    class Error<T>(message: String, data: T? = null) : NewResource<T>(data, message)
    class Loading<T> : NewResource<T>()
}