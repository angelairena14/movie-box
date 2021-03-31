package com.test.moviebox.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.moviebox.room.model.FavouriteMovieModel

@Database(entities = [FavouriteMovieModel::class], version = 4, exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : DAOAccess

    companion object {
        @Volatile
        private var INSTANCE : MovieDatabase? = null

        fun getDatabaseClient(context: Context) : MovieDatabase {
            if (INSTANCE != null) return INSTANCE as MovieDatabase
            synchronized(this){
                INSTANCE = Room.databaseBuilder(context, MovieDatabase::class.java,"MOVIE_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE as MovieDatabase
            }
        }
    }
}