package com.whatmovie.app.compose.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.whatmovie.app.compose.data.database.model.MovieDetail

@Dao
abstract class MovieDetailEntityDao : BaseDao<MovieDetail> {
    @Query("SELECT * FROM movie_detail WHERE id = :id")
    abstract fun getMovie(id: Long): MovieDetail?
}