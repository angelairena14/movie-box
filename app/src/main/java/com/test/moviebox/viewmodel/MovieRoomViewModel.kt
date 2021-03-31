package com.test.moviebox.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.test.moviebox.repository.RoomRepository
import com.test.moviebox.room.model.FavouriteMovieModel

class MovieRoomViewModel : ViewModel() {
    var movieListLiveData: LiveData<List<FavouriteMovieModel>>? = null

    fun insertData(
        context: Context,
        item : FavouriteMovieModel
    ) {
        RoomRepository.insertData(context, item)
    }

    fun deleteData(
        context: Context,
        item : FavouriteMovieModel
    ) {
        RoomRepository.delete(context, item)
    }

    fun getMovieDetail(context: Context): LiveData<List<FavouriteMovieModel>>? {
        movieListLiveData = RoomRepository.getMovieDetail(context)
        return movieListLiveData
    }

    fun getMovieSingle(context: Context,movieId : Int): Int? {
        return RoomRepository.getSingleMovie(context,movieId)
    }
}