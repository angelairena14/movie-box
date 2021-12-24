package com.test.moviebox.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.utils.NewResource
import com.test.moviebox.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val movieDetail: MutableLiveData<NewResource<MovieListDetail>> = MutableLiveData()
    val movieRating: MutableLiveData<NewResource<MovieReviewResponse>> = MutableLiveData()
    val movieList: MutableLiveData<NewResource<MovieListResponse>> = MutableLiveData()

    fun loadData(id: Int, page: Int) {
        viewModelScope.launch {
//            testAsyncAwait(id,page)
            launch { fetchMovieDetailsDeffered(id) }
            launch { fetchMovieRatingDeffered(id, page) }
        }
    }

    suspend fun testAsyncAwait(id: Int, page: Int){
        viewModelScope.launch(IO){
            val data1 = async {
                try {
                   movieRepository.getMovieDetail(id)
                } catch (t: Throwable) {
                    null
                }
            }
            val data2 = async {
                try {
                   movieRepository.getMovieReviews(id, page)
                } catch (t: Throwable) {
                    null
                }
            }
            val resultDetail = data1.await()
            val resultReview = data2.await()
            if (resultDetail != null && resultReview != null){
                Log.i("result_is","movie_detail ${resultDetail.overview}")
                Log.i("result_is","movie_rating ${resultReview.total_pages}")
            }
        }
    }

    fun getMovieDetailsObject(): LiveData<NewResource<MovieListDetail>> {
        return movieDetail
    }

    // SHOULD BE LIKE THIS TO MAKE UNIT TEST
    suspend fun fetchMovieDetailsDeffered(id: Int) {
        viewModelScope.launch(IO) {
            movieDetail.postValue(NewResource.Loading())
            try {
                val resultDef = movieRepository.getMovieDetail(id)
                movieDetail.postValue(NewResource.Success(resultDef))
            } catch (t: Throwable) {
                movieDetail.postValue(NewResource.Error(t.localizedMessage, null))
            }
        }
    }

    suspend fun fetchMovieRatingDeffered(id: Int, page: Int) {
        viewModelScope.launch(IO) {
            Log.i("thread_is","movie rating ${Thread.currentThread().name}")
            movieRating.postValue(NewResource.Loading())
            try {
                val resultDef = movieRepository.getMovieReviews(id, page)
                movieRating.postValue(NewResource.Success(resultDef))
            } catch (t: Throwable) {
                movieRating.postValue(NewResource.Error(t.localizedMessage, null))
            }
        }
    }

    // --------------------------------------------------------------------
    suspend fun fetchMovieListNew(page: Int) {
        viewModelScope.launch {
            movieList.postValue(NewResource.Loading())
            try {
                val resultDef = movieRepository.getMovieList(page)
                movieList.postValue(NewResource.Success(resultDef))
            } catch (t: Throwable) {
                movieList.postValue(NewResource.Error(t.localizedMessage, null))
            }
        }
    }

    // OLD CODE

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