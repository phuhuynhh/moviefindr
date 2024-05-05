/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whatmovie.app.compose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.whatmovie.app.compose.data.database.dao.MovieDetailEntityDao
import com.whatmovie.app.compose.data.database.dao.MoviePageDao
import com.whatmovie.app.compose.data.database.dao.MoviesDao
import com.whatmovie.app.compose.data.database.model.Movie
import com.whatmovie.app.compose.data.database.model.MovieDetail
import com.whatmovie.app.compose.data.database.model.MoviePage

/**
 * The [RoomDatabase] we use in this app.
 */
@Database(
    entities = [
        Movie::class,
        MovieDetail::class,
        MoviePage::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(RoomConverter::class)
abstract class WhatMovieDatabase : RoomDatabase() {
    abstract fun movieDetailDao(): MovieDetailEntityDao
    abstract fun moviesDao(): MoviesDao
    abstract fun pageDao(): MoviePageDao

}
