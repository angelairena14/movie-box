package com.test.moviebox.di

import com.test.moviebox.AppModule
import com.test.moviebox.repository.MovieListRepository
import com.test.moviebox.viewmodel.MovieListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface APIComponent {
    fun inject(movieListRepository: MovieListRepository)
    fun inject(movieListViewModelFactory: MovieListViewModelFactory)
}