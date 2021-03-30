package com.test.moviebox.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.test.moviebox.room.DAOAccess
import com.test.moviebox.room.MovieDatabase
import com.test.moviebox.room.model.FavouriteMovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: MovieDatabase
    private lateinit var dao: DAOAccess


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao()
    }


    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `add favourite movie`() = runBlockingTest {
        val movieItem = FavouriteMovieModel(12345, "Test", "2020-08-05", "test test","/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg")
        dao.insertData(movieItem)

        val allShoppingItems = dao.getMovieDetail().getOrAwaitValue()
        Assert.assertTrue(allShoppingItems.contains(movieItem))
    }


    @Test
    fun `delete favourite movie`() = runBlockingTest {
        val movieItem = FavouriteMovieModel(12345, "Test", "2020-08-05", "test test","/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg")
        dao.insertData(movieItem)
        dao.delete(movieItem)

        val allShoppingItems = dao.getMovieDetail().getOrAwaitValue()
        Assert.assertFalse(allShoppingItems.contains(movieItem))
    }
}