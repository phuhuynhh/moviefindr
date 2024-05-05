package com.whatmovie.app.compose.data.repositories

import com.whatmovie.app.compose.data.database.dao.MoviePageDao
import com.whatmovie.app.compose.data.database.dao.MoviesDao
import com.whatmovie.app.compose.data.database.model.Movie
import com.whatmovie.app.compose.data.database.model.MoviePage
import com.whatmovie.app.compose.domain.models.PaginatedList
import javax.inject.Inject

interface MoviesStorage {
    suspend fun saveMovies(movies: List<Movie>, page: Int)
    fun getMoviesByPage(page: Int = 1): PaginatedList<Movie>?
}

class LocalMoviesStorage @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviePageDao: MoviePageDao
) : MoviesStorage {
    override suspend fun saveMovies(movies: List<Movie>, page: Int) {
        moviesDao.insertAll(movies)
        val ids = movies.map { it.id }
        moviePageDao.insert(MoviePage(page, ids))

    }

    override fun getMoviesByPage(page: Int): PaginatedList<Movie>? {
        val movies = moviesDao.getMoviesForPage(page)
        if (movies.isEmpty()) return null
        val totalPage = moviePageDao.getTotalPages()
        val totalResult = moviesDao.getTotalMovies()
        val pageData = PaginatedList(
            page,
            movies,
            totalPage,
            totalResult
        )
        return pageData
    }
}