package com.test.moviebox.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movies")
data class FavouriteMovieModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    var movie_id : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "release_date")
    var releaseDate : String,
    @ColumnInfo(name = "overview")
    var overview : String,
    @ColumnInfo(name = "poster_path")
    var posterPath : String
)