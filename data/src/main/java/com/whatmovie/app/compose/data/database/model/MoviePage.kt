package com.whatmovie.app.compose.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_page")
data class MoviePage(
    @PrimaryKey
    val page: Int,
    @ColumnInfo(name = "movie_ids")
    val movieIds: List<Long>,
    val updatedAt: Long = System.currentTimeMillis()
)