package com.test.moviebox.repository

import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

abstract class MovieRepository {
    abstract suspend fun getMovieList(page: Int): MovieListResponse

    abstract suspend fun getPopularMovies(page: Int): MovieListResponse

    abstract suspend fun getUpcomingMovie(page: Int): MovieListResponse

    abstract suspend fun getTopRatedMovie(page: Int): MovieListResponse

    abstract suspend fun getNowPlayingMovie(page: Int): MovieListResponse

    abstract suspend fun getMovieDetail(movieId: Int): MovieListDetail

    abstract suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviewResponse

}