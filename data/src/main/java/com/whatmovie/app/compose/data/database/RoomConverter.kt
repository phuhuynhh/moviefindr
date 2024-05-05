package com.whatmovie.app.compose.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.whatmovie.app.compose.domain.models.Collection
import com.whatmovie.app.compose.domain.models.Genre
import com.whatmovie.app.compose.domain.models.ProductionCompany
import com.whatmovie.app.compose.domain.models.ProductionCountry
import com.whatmovie.app.compose.domain.models.SpokenLanguage
import javax.inject.Inject

@ProvidedTypeConverter
class RoomConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromListInteger(data: List<Int>): String {
        return moshi.adapter<List<Int>>(
            Types.newParameterizedType(
                List::class.java,
                Int::class.javaObjectType
            )
        ).toJson(data)
    }

    @TypeConverter
    fun toListInteger(json: String): List<Int> {
        return (moshi.adapter<List<Int>>(
            Types.newParameterizedType(
                List::class.java,
                Int::class.javaObjectType
            )
        ).fromJson(json))!!
    }

    @TypeConverter
    fun fromListLong(data: List<Long>): String {
        return moshi.adapter<List<Long>>(
            Types.newParameterizedType(
                List::class.java,
                Long::class.javaObjectType
            )
        ).toJson(data)
    }

    @TypeConverter
    fun toListLong(json: String): List<Long> {
        return (moshi.adapter<List<Long>>(
            Types.newParameterizedType(
                List::class.java,
                Long::class.javaObjectType
            )
        ).fromJson(json))!!
    }

    @TypeConverter
    fun fromCollection(data: Collection): String {
        return moshi.adapter(Collection::class.java).toJson(data)
    }

    @TypeConverter
    fun toCollection(json: String): Collection {
        return (moshi.adapter<Collection>(Collection::class.java).fromJson(json))!!
    }

    @TypeConverter
    fun fromGenre(data: Genre): String {
        return moshi.adapter(Genre::class.java).toJson(data)
    }

    @TypeConverter
    fun toGenre(json: String): Genre {
        return (moshi.adapter<Genre>(Genre::class.java).fromJson(json))!!
    }

    @TypeConverter
    fun fromListProductionCountry(data: List<ProductionCountry>): String {
        return moshi.adapter<List<ProductionCountry>>(
            Types.newParameterizedType(
                List::class.java,
                ProductionCountry::class.java
            )
        ).toJson(data)
    }

    @TypeConverter
    fun toListProductionCountry(json: String): List<ProductionCountry> {
        return (moshi.adapter<List<ProductionCountry>>(
            Types.newParameterizedType(
                List::class.java,
                ProductionCountry::class.java
            )
        ).fromJson(json))!!
    }

    @TypeConverter
    fun fromListProductionCompany(data: List<ProductionCompany>): String {
        return moshi.adapter<List<ProductionCompany>>(
            Types.newParameterizedType(
                List::class.java,
                ProductionCompany::class.java
            )
        ).toJson(data)
    }

    @TypeConverter
    fun toListProductionCompany(json: String): List<ProductionCompany> {
        return (moshi.adapter<List<ProductionCompany>>(
            Types.newParameterizedType(
                List::class.java,
                ProductionCompany::class.java
            )
        ).fromJson(json))!!
    }

    @TypeConverter
    fun fromListSpokenLanguage(data: List<SpokenLanguage>): String {
        return moshi.adapter<List<SpokenLanguage>>(
            Types.newParameterizedType(
                List::class.java,
                SpokenLanguage::class.java
            )
        ).toJson(data)
    }

    @TypeConverter
    fun toListSpokenLanguage(json: String): List<SpokenLanguage> {
        return (moshi.adapter<List<SpokenLanguage>>(
            Types.newParameterizedType(
                List::class.java,
                SpokenLanguage::class.java
            )
        ).fromJson(json))!!
    }

    @TypeConverter
    fun fromListGenre(data: List<Genre>): String {
        return moshi.adapter<List<Genre>>(
            Types.newParameterizedType(
                List::class.java,
                Genre::class.java
            )
        ).toJson(data)
    }

    @TypeConverter
    fun toListGenre(json: String): List<Genre> {
        return (moshi.adapter<List<Genre>>(
            Types.newParameterizedType(
                List::class.java,
                Genre::class.java
            )
        ).fromJson(json))!!
    }


}