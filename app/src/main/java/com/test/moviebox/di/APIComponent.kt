package com.test.moviebox.di

import com.test.moviebox.AppModule
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.repository.MovieRepositoryImpl
import com.test.moviebox.utils.BaseWorker
import com.test.moviebox.viewmodel.MovieViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface APIComponent {
    fun inject(movieRepository: MovieRepository)
    fun inject(workder : BaseWorker)
    fun inject(movieViewModelFactory: MovieViewModelFactory)
    fun inject(movieRepositoryImpl: MovieRepositoryImpl)
}