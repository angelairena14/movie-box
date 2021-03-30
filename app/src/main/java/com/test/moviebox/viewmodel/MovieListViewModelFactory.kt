package com.test.moviebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.moviebox.BaseApplication
import com.test.moviebox.repository.MovieListRepository
import javax.inject.Inject

class MovieListViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofitRepository: MovieListRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var apiComponent = BaseApplication().getMyComponent()
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)){
            return MovieListViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}