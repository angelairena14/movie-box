package com.test.moviebox.repository

import com.test.moviebox.BaseApplication
import com.test.moviebox.BuildConfig
import com.test.moviebox.di.APIComponent
import io.reactivex.disposables.Disposables
import retrofit2.Retrofit
import javax.inject.Inject

class MovieListRepository {
    @Inject
    lateinit var retrofit : Retrofit
    var apiService : APIService

    init {
        val apiComponent : APIComponent = BaseApplication().getMyComponent()
        apiComponent.inject(this)
        apiService = retrofit.create(APIService::class.java)
    }

    suspend fun getPopularMovies(page : Int) = apiService.getPopularMovieList(BuildConfig.API_KEY,page)

    suspend fun getUpcomingMovie(page : Int) = apiService.getUpcomingMovieList(BuildConfig.API_KEY,page)

    suspend fun getTopRatedMoview(page : Int) = apiService.getTopRatedMovieList(BuildConfig.API_KEY,page)

    suspend fun getNowPlayingMovie(page : Int) = apiService.getNowPlayingMovieList(BuildConfig.API_KEY,page)



}