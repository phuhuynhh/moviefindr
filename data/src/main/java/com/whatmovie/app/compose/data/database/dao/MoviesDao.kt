package com.whatmovie.app.compose.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.whatmovie.app.compose.data.database.model.Movie
import com.whatmovie.app.compose.data.database.model.MoviePage


/**
 * [Room] DAO for [Movie] related operations.
 */
@Dao
interface MoviesDao : BaseDao<Movie> {
    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Long): Movie?


    @Query("SELECT * FROM movie_page WHERE page = :page")
    fun getMovieIdsForPage(page: Int): MoviePage?

    @Transaction
    fun getMoviesForPage(page: Int): List<Movie> {
        return getMovieIdsForPage(page)?.let {
            it.movieIds.mapNotNull { id ->
                getMovieById(id)
            }
        } ?: listOf()
    }

    // Query to get total results
    @Query("SELECT SUM(LENGTH(id)) FROM movies")
    abstract fun getTotalMovies(): Int
}