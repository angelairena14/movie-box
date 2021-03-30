package com.test.moviebox.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moneymanager.utils.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.repository.MovieListRepository
import com.test.moviebox.viewmodel.MovieListViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieListRepository: MovieListRepository
    private lateinit var popularMoviesObserver: Observer<Resource<MovieListResponse>>
    private var movieList = getPopularMovieResponse()
    private val successResources = Resource.success(getPopularMovieResponse())
    var page = 1

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieListRepository = mock()
        runBlocking {
            whenever(movieListRepository.getPopularMovies(page)).thenReturn(movieList)
        }
        viewModel = MovieListViewModel(movieListRepository)
        popularMoviesObserver = mock()
    }


    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `success fetching popular movies`() = runBlocking {
        viewModel.fetchPopularMovies(page).observeForever(popularMoviesObserver)
        verify(popularMoviesObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(popularMoviesObserver, timeout(6000)).onChanged(successResources)
    }
}