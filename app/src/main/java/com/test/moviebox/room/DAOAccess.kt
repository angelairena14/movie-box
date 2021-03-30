package com.test.moviebox.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.moviebox.room.model.FavouriteMovieModel

@Dao
interface DAOAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(loginTableModel: FavouriteMovieModel)

    @Delete
    suspend fun delete(loginTableModel: FavouriteMovieModel)

    @Query("SELECT * FROM fav_movies")
    fun getMovieDetail() : LiveData<List<FavouriteMovieModel>>
}