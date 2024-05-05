package com.whatmovie.app.compose.data.repositories

import com.whatmovie.app.compose.data.database.dao.MovieDetailEntityDao
import com.whatmovie.app.compose.data.database.model.MovieDetail
import timber.log.Timber
import javax.inject.Inject

interface MovieDetailStorage {
    suspend fun saveMovieDetail(movie: MovieDetail)
    fun getMovieDetail(movieId: Long): MovieDetail?
}

class LocalMovieDetailStorage @Inject constructor(
    private val movieDetailDao: MovieDetailEntityDao
) : MovieDetailStorage {
    override suspend fun saveMovieDetail(movie: MovieDetail) {
        movieDetailDao.insert(movie)
    }

    override fun getMovieDetail(movieId: Long): MovieDetail? {
        Timber.d("Get Local MovieDetail : $movieId")
        return movieDetailDao.getMovie(movieId)
    }
}