package com.test.moviebox.repository

import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("/3/discover/movie")
    suspend fun getMovieList(@Query("api_key") api_key: String,
                                    @Query("page") page: Int): MovieListResponse

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

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id: Int, @Query("api_key") api_key: String): MovieListDetail

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") id: Int, @Query("api_key") api_key: String): MovieReviewResponse
}