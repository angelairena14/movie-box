package com.test.moviebox.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.test.moviebox.room.MovieDatabase
import com.test.moviebox.room.model.FavouriteMovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RoomRepository {
    companion object {
        var movieDatabase: MovieDatabase? = null
        var movieTableModel: LiveData<List<FavouriteMovieModel>>? = null

        private fun initializeDB(context: Context): MovieDatabase {
            return MovieDatabase.getDatabaseClient(context)
        }

        fun insertData(
            context: Context,
            item : FavouriteMovieModel
        ) {
            movieDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                movieDatabase?.movieDao()?.insertData(item)
            }
        }

        fun delete(
            context: Context,
            item : FavouriteMovieModel
        ) {
            movieDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                movieDatabase?.movieDao()?.delete(item)
            }
        }

        fun getMovieDetail(context: Context): LiveData<List<FavouriteMovieModel>>? {
            movieDatabase = initializeDB(context)
            movieTableModel = movieDatabase?.movieDao()?.getMovieDetail()
            return movieTableModel
        }
    }
}