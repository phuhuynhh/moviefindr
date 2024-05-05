package com.whatmovie.app.compose.di.modules

import com.whatmovie.app.compose.data.repositories.MovieDetailStorage
import com.whatmovie.app.compose.data.remote.services.ApiService
import com.whatmovie.app.compose.data.repositories.MovieDetailRepositoryImpl
import com.whatmovie.app.compose.data.repositories.MoviesRepositoryImpl
import com.whatmovie.app.compose.data.repositories.MoviesStorage
import com.whatmovie.app.compose.domain.DispatchersProvider
import com.whatmovie.app.compose.domain.repositories.MovieDetailRepository
import com.whatmovie.app.compose.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideMoviesRepository(
        moviesStorage: MoviesStorage,
        apiService: ApiService,
        dispatchersProvider: DispatchersProvider
    ): MoviesRepository = MoviesRepositoryImpl(moviesStorage, apiService, dispatchersProvider)

    @Provides
    fun provideMovieDetailRepository(
        movieDetailStorage: MovieDetailStorage,
        apiService: ApiService,
        dispatchersProvider: DispatchersProvider
    ): MovieDetailRepository =
        MovieDetailRepositoryImpl(movieDetailStorage, apiService, dispatchersProvider)
}
