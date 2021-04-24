package com.test.moviebox.viewmodel

import androidx.lifecycle.*
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.utils.NewResource
import com.test.moviebox.utils.Resource
import kotlinx.coroutines.*


class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val movieDetail: MutableLiveData<NewResource<MovieListDetail>> = MutableLiveData()
    val movieRating: MutableLiveData<NewResource<MovieReviewResponse>> = MutableLiveData()
    val movieList: MutableLiveData<NewResource<MovieListResponse>> = MutableLiveData()

    suspend fun getDeferredMovieDetail(id: Int) = withContext(Dispatchers.IO) {
        movieRepository.getMovieDetail(id)
    }

    suspend fun getDeferredMovieRating(id: Int, page: Int) = withContext(Dispatchers.IO) {
        movieRepository.getMovieReviews(id, page)
    }

    suspend fun getDeferredMovieList(page: Int) = withContext(Dispatchers.IO) {
        movieRepository.getMovieList(page)
    }

    fun loadData(id: Int, page: Int) {
        viewModelScope.launch {
            coroutineScope {
                val data1 = async { fetchMovieDetailsDeffered(id) }
                val data2 = async { fetchMovieRatingDeffered(id, page) }
                data1.await()
                data2.await()
            }
        }
    }

    fun getMovieDetailsObject(): LiveData<NewResource<MovieListDetail>> {
        return movieDetail
    }

    private suspend fun fetchMovieDetailsDeffered(id: Int) {
        viewModelScope.launch {
            movieDetail.postValue(NewResource.Loading())
            try {
                val resultDef = getDeferredMovieDetail(id)
                movieDetail.postValue(NewResource.Success(resultDef))
            } catch (t: Throwable) {
                movieDetail.postValue(NewResource.Error(t.localizedMessage, null))
            }
        }
    }

    suspend fun fetchMovieRatingDeffered(id: Int, page: Int) {
        viewModelScope.launch {
            movieRating.postValue(NewResource.Loading())
            try {
                val resultDef = getDeferredMovieRating(id, page)
                movieRating.postValue(NewResource.Success(resultDef))
            } catch (t: Throwable) {
                movieRating.postValue(NewResource.Error(t.localizedMessage, null))
            }
        }
    }

    suspend fun fetchMovieListNew(page: Int) {
        viewModelScope.launch {
            movieList.postValue(NewResource.Loading())
            try {
                val resultDef = getDeferredMovieList(page)
                movieList.postValue(NewResource.Success(resultDef))
            } catch (t: Throwable) {
                movieList.postValue(NewResource.Error(t.localizedMessage, null))
            }
        }
    }

    fun fetchMovieList(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getMovieList(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchPopularMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getPopularMovies(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchUpComingMovie(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getUpcomingMovie(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchTopRatedMovie(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getTopRatedMovie(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchNowPlayingMovie(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getNowPlayingMovie(page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchMovieDetail(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getMovieDetail(movieId)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchMovieReview(movieId: Int, page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = movieRepository.getMovieReviews(movieId, page)
            emit(Resource.success(data = response))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}