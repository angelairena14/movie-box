package com.test.moviebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.moviebox.BaseApplication
import com.test.moviebox.repository.MovieRepository
import javax.inject.Inject

class MovieViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var movieRepository: MovieRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var apiComponent = BaseApplication().getMyComponent()
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}