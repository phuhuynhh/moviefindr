package com.whatmovie.app.compose.di.modules

import android.content.Context
import androidx.room.Room
import com.whatmovie.app.compose.data.database.RoomConverter
import com.whatmovie.app.compose.data.database.WhatMovieDatabase
import com.whatmovie.app.compose.data.database.dao.MovieDetailEntityDao
import com.whatmovie.app.compose.data.database.dao.MoviePageDao
import com.whatmovie.app.compose.data.database.dao.MoviesDao
import com.whatmovie.app.compose.data.repositories.LocalMovieDetailStorage
import com.whatmovie.app.compose.data.repositories.LocalMoviesStorage
import com.whatmovie.app.compose.data.repositories.MovieDetailStorage
import com.whatmovie.app.compose.data.repositories.MoviesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        typeConverter: RoomConverter
    ): WhatMovieDatabase =
        Room.databaseBuilder(context, WhatMovieDatabase::class.java, "whatmovie.db")
            .addTypeConverter(typeConverter)
            .build()


    @Provides
    @Singleton
    fun provideMovieDetailDao(
        database: WhatMovieDatabase
    ): MovieDetailEntityDao = database.movieDetailDao()


    @Provides
    @Singleton
    fun provideMoviesDao(
        database: WhatMovieDatabase
    ): MoviesDao = database.moviesDao()

    @Provides
    @Singleton
    fun provideMoviePageDao(
        database: WhatMovieDatabase
    ): MoviePageDao = database.pageDao()

    //    Data Store

    @Provides
    @Singleton
    fun provideMovieDetailStore(
        movieDetailDao: MovieDetailEntityDao,
    ): MovieDetailStorage = LocalMovieDetailStorage(
        movieDetailDao = movieDetailDao
    )

    @Provides
    @Singleton
    fun provideMoviesStore(
        moviesDao: MoviesDao,
        moviePage: MoviePageDao,
    ): MoviesStorage = LocalMoviesStorage(
        moviesDao = moviesDao,
        moviePageDao= moviePage
    )
}
