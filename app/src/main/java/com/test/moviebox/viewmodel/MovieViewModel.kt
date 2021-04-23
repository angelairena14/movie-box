package com.test.moviebox.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.utils.NewResource
import com.test.moviebox.utils.Resource
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MovieViewModel (private val movieRepository: MovieRepository) : ViewModel(){
    val movieDetail: MutableLiveData<NewResource<MovieListDetail>> = MutableLiveData()
    val movieRating: MutableLiveData<NewResource<MovieReviewResponse>> = MutableLiveData()

    suspend fun getMovieDetails(id: Int) = viewModelScope.launch {
        fetchMovieDetails(id)
    }

    suspend fun getMovieReviews(id: Int,page: Int) = viewModelScope.launch {
        fetchMovieReviews(id,page)
    }

    fun loadData(id : Int, page : Int){
        viewModelScope.launch {
            val data1 = async {  getMovieDetails(id)}
            val data2 = async { getMovieReviews(id,page) }
            data1.await()
            data2.await()
        }
    }

    fun getMovieDetailsObject(): LiveData<NewResource<MovieListDetail>> {
        return movieDetail
    }
    private suspend fun fetchMovieDetails(id : Int) {
        movieDetail.postValue(NewResource.Loading())
        try {
            val response = movieRepository.getMovieDetail(id)
            movieDetail.postValue(NewResource.Success(response))
        } catch (t: Throwable) {
            movieDetail.postValue(NewResource.Error(t.localizedMessage,null))
        }
    }

    private suspend fun fetchMovieReviews(id : Int, page : Int) {
        viewModelScope.launch(Dispatchers.IO){
            movieRating.postValue(NewResource.Loading())
            try {
                val response = movieRepository.getMovieReviews(id,page)
                movieRating.postValue(NewResource.Success(response))
            } catch (t: Throwable) {
                movieRating.postValue(NewResource.Error(t.localizedMessage,null))
            }
        }
    }

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