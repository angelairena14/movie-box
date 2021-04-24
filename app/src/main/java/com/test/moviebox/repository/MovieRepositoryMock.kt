package com.test.moviebox.repository

import com.test.moviebox.BuildConfig
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import retrofit2.Response

class MovieRepositoryMock(val apiServiceMock : APIService) : MovieRepository(){
    override suspend fun getMovieList(page: Int) = apiServiceMock.getMovieList(BuildConfig.API_KEY,page)

    override suspend fun getPopularMovies(page: Int): MovieListResponse =  apiServiceMock.getPopularMovieList(BuildConfig.API_KEY,page)

    override suspend fun getUpcomingMovie(page: Int): MovieListResponse =  apiServiceMock.getUpcomingMovieList(BuildConfig.API_KEY,page)

    override suspend fun getTopRatedMovie(page: Int): MovieListResponse =  apiServiceMock.getTopRatedMovieList(BuildConfig.API_KEY,page)

    override suspend fun getNowPlayingMovie(page: Int): MovieListResponse =  apiServiceMock.getNowPlayingMovieList(BuildConfig.API_KEY,page)

    override suspend fun getMovieDetail(movieId: Int): MovieListDetail  = apiServiceMock.getMovieDetail(movieId,BuildConfig.API_KEY)

    override suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviewResponse = apiServiceMock.getMovieReviews(movieId,page,BuildConfig.API_KEY)

}