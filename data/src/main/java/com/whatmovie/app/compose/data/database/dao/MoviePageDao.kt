package com.whatmovie.app.compose.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.whatmovie.app.compose.data.database.model.MoviePage

@Dao
abstract class MoviePageDao : BaseDao<MoviePage> {
    // Query to get total pages
    @Query("SELECT MAX(page) FROM movie_page")
    abstract fun getTotalPages(): Int
}