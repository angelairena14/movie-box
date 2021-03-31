package com.test.moviebox.utils

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.test.moviebox.BuildConfig
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.repository.APIService
import com.test.moviebox.repository.MovieRepository
import com.test.moviebox.repository.MovieRepositoryMock
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import kotlin.math.exp


@RunWith(JUnit4::class)
class MovieRepositoryTest {
    private lateinit var movieApi: APIService
    private lateinit var repository: MovieRepository
    lateinit var movieList : MovieListResponse
    lateinit var movieDetail : MovieListDetail
    lateinit var movieReview : MovieReviewResponse
    var page = 1
    var invalidPage = -11
    var movieId = 791373
    var invalidMovieId = -1

    @Before
    fun setUp() {
        movieApi = mock()
        movieList = mock()
        movieDetail = mock()
        movieReview = mock()
        repository = MovieRepositoryMock(movieApi)
    }

    @Test
    fun `success get movie list`() =
        runBlocking {
            val expected = movieList
            whenever(movieApi.getMovieList(BuildConfig.API_KEY, page)).thenReturn(expected)
            val result = repository.getMovieList(page)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get movie list`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getMovieList(BuildConfig.API_KEY, invalidPage)).thenAnswer { throw expected }
            try {
                val repos = repository.getMovieList(invalidPage)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }

    @Test
    fun `success get popular movie list`() =
        runBlocking {
            val expected = movieList
            whenever(movieApi.getPopularMovieList(BuildConfig.API_KEY, page)).thenReturn(expected)
            val result = repository.getPopularMovies(page)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get popular movie list`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getPopularMovieList(BuildConfig.API_KEY, invalidPage)).thenAnswer { throw expected }
            try {
                val repos = repository.getPopularMovies(invalidPage)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }

    @Test
    fun `success get upcoming movie list`() =
        runBlocking {
            val expected = movieList
            whenever(movieApi.getUpcomingMovieList(BuildConfig.API_KEY, page)).thenReturn(expected)
            val result = repository.getUpcomingMovie(page)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get upcoming movie list`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getUpcomingMovieList(BuildConfig.API_KEY, invalidPage)).thenAnswer { throw expected }
            try {
                val repos = repository.getUpcomingMovie(invalidPage)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }

    @Test
    fun `success get top rated movie list`() =
        runBlocking {
            val expected = movieList
            whenever(movieApi.getTopRatedMovieList(BuildConfig.API_KEY, page)).thenReturn(expected)
            val result = repository.getTopRatedMovie(page)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get top rated movie list`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getTopRatedMovieList(BuildConfig.API_KEY, invalidPage)).thenAnswer { throw expected }
            try {
                val repos = repository.getTopRatedMovie(invalidPage)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }

    @Test
    fun `success get now playing movie list`() =
        runBlocking {
            val expected = movieList
            whenever(movieApi.getNowPlayingMovieList(BuildConfig.API_KEY, page)).thenReturn(expected)
            val result = repository.getNowPlayingMovie(page)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get now playing movie list`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getNowPlayingMovieList(BuildConfig.API_KEY, invalidPage)).thenAnswer { throw expected }
            try {
                val repos = repository.getNowPlayingMovie(invalidPage)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }

    @Test
    fun `success get movie detail`() =
        runBlocking {
            val expected = movieDetail
            whenever(movieApi.getMovieDetail(movieId,BuildConfig.API_KEY)).thenReturn(expected)
            val result = repository.getMovieDetail(movieId)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get movie detail`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getMovieDetail(invalidMovieId,BuildConfig.API_KEY)).thenAnswer { throw expected }
            try {
                val repos = repository.getMovieDetail(invalidMovieId)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }

    @Test
    fun `success get movie review`() =
        runBlocking {
            val expected = movieReview
            whenever(movieApi.getMovieReviews(movieId,page,BuildConfig.API_KEY)).thenReturn(expected)
            val result = repository.getMovieReviews(movieId,page)
            assertEquals(result,expected)
        }

    @Test
    fun `failed get movie review`() =
        runBlocking {
            val expected = HttpException(Response.error<Any>(422,"".toResponseBody()))
            whenever(movieApi.getMovieReviews(invalidMovieId,invalidPage,BuildConfig.API_KEY)).thenAnswer { throw expected }
            try {
                val repos = repository.getMovieReviews(invalidMovieId,invalidPage)
            } catch (e: HttpException){
                assertTrue(e.code() !=200)
            }
        }
}