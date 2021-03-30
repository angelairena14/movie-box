package com.test.moviebox.utils

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.test.moviebox.BuildConfig
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.repository.APIService
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.viewmodel.MovieViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException


@RunWith(JUnit4::class)
class MovieRepositoryTest {
    private lateinit var movieApi: APIService
    private lateinit var repository: MovieRepository
    lateinit var movieList : MovieListResponse
    lateinit var movieDetail : MovieListDetail
    lateinit var movieReview : MovieReviewResponse
    var page = 1
    var movieId = 791373

    @Before
    fun setUp() {
        movieApi = mock()
        movieList = mock()
        movieDetail = mock()
        movieReview = mock()
        runBlocking {
            whenever(movieApi.getMovieList(BuildConfig.API_KEY, page)).thenReturn(movieList)
            whenever(movieApi.getPopularMovieList(BuildConfig.API_KEY, page)).thenReturn(movieList)
            whenever(movieApi.getUpcomingMovieList(BuildConfig.API_KEY, page)).thenReturn(movieList)
            whenever(movieApi.getTopRatedMovieList(BuildConfig.API_KEY, page)).thenReturn(movieList)
            whenever(movieApi.getNowPlayingMovieList(BuildConfig.API_KEY, page)).thenReturn(movieList)
            whenever(movieApi.getMovieDetail(movieId,BuildConfig.API_KEY)).thenReturn(movieDetail)
            whenever(movieApi.getMovieReviews(movieId,BuildConfig.API_KEY)).thenReturn(movieReview)
        }
        repository = MovieRepository()
    }

    @Test
    fun `success get movie list`() =
        runBlocking {
            assertTrue(repository.getMovieList(page) != null)
        }

    @Test
    fun `success get popular movie list`() =
        runBlocking {
            assertTrue(repository.getPopularMovies(page) != null)
        }

    @Test
    fun `success get upcoming movie list`() =
        runBlocking {
            assertTrue(repository.getUpcomingMovie(page) != null)
        }

    @Test
    fun `success get top rated movie list`() =
        runBlocking {
            assertTrue(repository.getTopRatedMoview(page) != null)
        }

    @Test
    fun `success get now playing movie list`() =
        runBlocking {
            assertTrue(repository.getNowPlayingMovie(page) != null)
        }

    @Test
    fun `success get movie detail`() =
        runBlocking {
            assertTrue(repository.getMovieDetail(movieId) != null)
        }

    @Test
    fun `success get movie review`() =
        runBlocking {
            assertTrue(repository.getMovieReviews(movieId) != null)
        }
}