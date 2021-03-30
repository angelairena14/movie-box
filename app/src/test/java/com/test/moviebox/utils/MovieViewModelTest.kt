package com.test.moviebox.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.viewmodel.MovieViewModel
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
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieObserver: Observer<Resource<MovieListResponse>>
    private lateinit var movieDetailObserver: Observer<Resource<MovieListDetail>>
    private lateinit var movieReviewObserver: Observer<Resource<MovieReviewResponse>>
    lateinit var movieList : MovieListResponse
    lateinit var movieDetail : MovieListDetail
    lateinit var movieReview : MovieReviewResponse
    lateinit var successResourcesMovieList : Resource<MovieListResponse>
    lateinit var successResourceMovieDetail : Resource<MovieListDetail>
    lateinit var successResourceMovieReview : Resource<MovieReviewResponse>
    private var page = 1
    var movieId = 791373

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
        movieRepository = mock()
        movieDetail = mock()
        movieList = mock()
        movieReview = mock()
        viewModel = MovieViewModel(movieRepository)
        movieObserver = mock()
        movieDetailObserver = mock()
        movieReviewObserver = mock()
        successResourcesMovieList = Resource.success(movieList)
        successResourceMovieDetail = Resource.success(movieDetail)
        successResourceMovieReview = Resource.success(movieReview)
        runBlocking {
            whenever(movieRepository.getMovieList(page)).thenReturn(movieList)
            whenever(movieRepository.getPopularMovies(page)).thenReturn(movieList)
            whenever(movieRepository.getUpcomingMovie(page)).thenReturn(movieList)
            whenever(movieRepository.getTopRatedMoview(page)).thenReturn(movieList)
            whenever(movieRepository.getNowPlayingMovie(page)).thenReturn(movieList)
            whenever(movieRepository.getMovieDetail(movieId)).thenReturn(movieDetail)
            whenever(movieRepository.getMovieReviews(movieId,page)).thenReturn(movieReview)
        }
    }


    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun `success load movie list`() = runBlocking {
        viewModel.fetchMovieList(page).observeForever(movieObserver)
        verify(movieObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(6000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `success load popular movie list`() = runBlocking {
        viewModel.fetchPopularMovies(page).observeForever(movieObserver)
        verify(movieObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(6000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `success load upcoming movie list`() = runBlocking {
        viewModel.fetchUpComingMovie(page).observeForever(movieObserver)
        verify(movieObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(6000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `success load top rated movie list`() = runBlocking {
        viewModel.fetchTopRatedMovie(page).observeForever(movieObserver)
        verify(movieObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(6000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `success load now playing movie list`() = runBlocking {
        viewModel.fetchNowPlayingMovie(page).observeForever(movieObserver)
        verify(movieObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(6000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `success load movie detail`() = runBlocking {
        viewModel.fetchMovieDetail(movieId).observeForever(movieDetailObserver)
        verify(movieDetailObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieDetailObserver, timeout(6000)).onChanged(successResourceMovieDetail)
    }

    @Test
    fun `success load movie review`() = runBlocking {
        viewModel.fetchMovieReview(movieId,page).observeForever(movieReviewObserver)
        verify(movieReviewObserver, timeout(6000)).onChanged(Resource.loading(null))
        verify(movieReviewObserver, timeout(6000)).onChanged(successResourceMovieReview)
    }
}