package com.test.moviebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.moneymanager.utils.Resource
import com.test.moviebox.repository.MovieListRepository
import kotlinx.coroutines.Dispatchers

class MovieListViewModel (retrofitRepository: MovieListRepository) : ViewModel(){
    private var retrofitRepository: MovieListRepository = retrofitRepository

    fun fetchPopularMovies(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = retrofitRepository.getPopularMovies(page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchUpComingMovie(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = retrofitRepository.getUpcomingMovie(page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchTopRatedMovie(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = retrofitRepository.getTopRatedMoview(page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchNowPlayingMovie(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = retrofitRepository.getNowPlayingMovie(page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}