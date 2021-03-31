package com.test.moviebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.utils.Resource
import kotlinx.coroutines.Dispatchers

class MovieViewModel (private val movieRepository: MovieRepository) : ViewModel(){

    fun fetchMovieList(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getMovieList(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchPopularMovies(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getPopularMovies(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchUpComingMovie(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getUpcomingMovie(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchTopRatedMovie(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getTopRatedMovie(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchNowPlayingMovie(page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getNowPlayingMovie(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchMovieDetail(movieId : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getMovieDetail(movieId)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchMovieReview(movieId : Int, page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getMovieReviews(movieId,page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}