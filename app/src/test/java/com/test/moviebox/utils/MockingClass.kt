package com.test.moviebox.utils

import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieListDetail

fun getPopularMovieResponse() : MovieListResponse {
    return MovieListResponse(1,ArrayList(),10,20)
}