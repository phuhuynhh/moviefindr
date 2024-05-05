package com.whatmovie.app.compose.data.remote.services

import com.whatmovie.app.compose.data.database.model.Movie
import com.whatmovie.app.compose.data.database.model.MovieDetail
import com.whatmovie.app.compose.data.remote.responses.ImageConfiguration
import com.whatmovie.app.compose.domain.models.PaginatedList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("page") page: Int = 1): Response<PaginatedList<Movie>>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") queryString: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): Response<PaginatedList<Movie>>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Long): Response<MovieDetail>

    @GET("configuration")
    suspend fun getImageConfiguration(): ImageConfiguration
}
