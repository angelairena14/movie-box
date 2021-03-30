package com.test.moviebox.repository

import com.test.moviebox.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovieList(@Query("api_key") api_key: String,
                                    @Query("page") page: Int): MovieListResponse

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovieList(@Query("api_key") api_key: String,
                                    @Query("page") page: Int): MovieListResponse

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovieList(@Query("api_key") api_key: String,
                                     @Query("page") page: Int): MovieListResponse

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovieList(@Query("api_key") api_key: String,
                                     @Query("page") page: Int): MovieListResponse
}