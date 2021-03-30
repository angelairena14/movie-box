package com.test.moviebox.di

import com.test.moviebox.AppModule
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.viewmodel.MovieViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface APIComponent {
    fun inject(movieRepository: MovieRepository)
    fun inject(movieViewModelFactory: MovieViewModelFactory)
}