package com.test.moviebox.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
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
    private lateinit var newMovieDetailObserver: Observer<NewResource<MovieListDetail>>
    private lateinit var movieReviewObserver: Observer<Resource<MovieReviewResponse>>
    lateinit var movieList : MovieListResponse
    lateinit var movieDetail : MovieListDetail
    lateinit var movieReview : MovieReviewResponse
    lateinit var successResourcesMovieList : Resource<MovieListResponse>
    lateinit var successResourceMovieDetail : Resource<MovieListDetail>
    lateinit var successNewResourceMovieDetail : NewResource<MovieListDetail>
    lateinit var successResourceMovieReview : Resource<MovieReviewResponse>
    private var page = 1
    private var invalidPage = -1
    private var movieId = 791373
    private var invalidMovieId = -1

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
        newMovieDetailObserver = mock()
        successResourcesMovieList = Resource.success(movieList)
        successResourceMovieDetail = Resource.success(movieDetail)
        successResourceMovieReview = Resource.success(movieReview)
        successNewResourceMovieDetail = NewResource.Success(
            MovieListDetail(1,"","","","","")
        )
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
        whenever(movieRepository.getMovieList(page)).thenReturn(movieList)
        viewModel.fetchMovieList(page).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `failed load movie list`() = runBlocking {
        whenever(movieRepository.getMovieList(-invalidPage)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchMovieList(-invalidPage).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }

    @Test
    fun `success load popular movie list`() = runBlocking {
        whenever(movieRepository.getPopularMovies(page)).thenReturn(movieList)
        viewModel.fetchPopularMovies(page).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `failed load popular movie list`() = runBlocking {
        whenever(movieRepository.getPopularMovies(-invalidPage)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchPopularMovies(-invalidPage).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }

    @Test
    fun `success load upcoming movie list`() = runBlocking {
        whenever(movieRepository.getUpcomingMovie(page)).thenReturn(movieList)
        viewModel.fetchUpComingMovie(page).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `failed load upcoming movie list`() = runBlocking {
        whenever(movieRepository.getUpcomingMovie(-invalidPage)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchUpComingMovie(-invalidPage).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }

    @Test
    fun `success load top rated movie list`() = runBlocking {
        whenever(movieRepository.getTopRatedMovie(page)).thenReturn(movieList)
        viewModel.fetchTopRatedMovie(page).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(successResourcesMovieList)
    }

    @Test
    fun `failed load top rated movie list`() = runBlocking {
        whenever(movieRepository.getTopRatedMovie(-invalidPage)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchTopRatedMovie(-invalidPage).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }


    @Test
    fun `success load now playing movie list`() = runBlocking {
        whenever(movieRepository.getNowPlayingMovie(page)).thenReturn(movieList)
        viewModel.fetchNowPlayingMovie(page).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(successResourcesMovieList)
    }


    @Test
    fun `failed load now playing movie list`() = runBlocking {
        whenever(movieRepository.getNowPlayingMovie(-invalidPage)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchNowPlayingMovie(-invalidPage).observeForever(movieObserver)
        verify(movieObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }

    @Test
    fun `success load movie detail`() = runBlocking {
        whenever(movieRepository.getMovieDetail(movieId)).thenReturn(movieDetail)
        viewModel.fetchMovieDetail(movieId).observeForever(movieDetailObserver)
        verify(movieDetailObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieDetailObserver, timeout(2000)).onChanged(successResourceMovieDetail)
    }

//    @Test
//    fun `success load movie detail 2`() = runBlocking {
//        whenever(movieRepository.getMovieDetail(movieId)).thenReturn(movieDetail)
//        viewModel.getMovieDetails(movieId)
//        viewModel.getMovieDetailsObject().observeForever(newMovieDetailObserver)
//        verify(newMovieDetailObserver, timeout(1000)).onChanged(refEq(NewResource.Loading()))
//    }

    @Test
    fun `failed load movie detail`() = runBlocking {
        whenever(movieRepository.getMovieDetail(-invalidMovieId)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchMovieDetail(-invalidMovieId).observeForever(movieDetailObserver)
        verify(movieDetailObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieDetailObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }

    @Test
    fun `success load movie review`() = runBlocking {
        whenever(movieRepository.getMovieReviews(movieId,page)).thenReturn(movieReview)
        viewModel.fetchMovieReview(movieId,page).observeForever(movieReviewObserver)
        verify(movieReviewObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieReviewObserver, timeout(2000)).onChanged(successResourceMovieReview)
    }

    @Test
    fun `failed load movie review`() = runBlocking {
        whenever(movieRepository.getMovieReviews(-invalidMovieId,invalidPage)).thenAnswer { throw Exception("Error!") }
        viewModel.fetchMovieReview(-invalidMovieId,invalidPage).observeForever(movieReviewObserver)
        verify(movieReviewObserver, timeout(1000)).onChanged(Resource.loading(null))
        verify(movieReviewObserver, timeout(2000)).onChanged(Resource.error(null,"Error!"))
    }
}