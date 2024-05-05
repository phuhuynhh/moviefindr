package com.whatmovie.app.compose.di.modules

import android.content.Context
import com.whatmovie.app.compose.data.remote.providers.MoshiBuilderProvider
import com.squareup.moshi.Moshi
import com.whatmovie.app.compose.data.database.RoomConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoshiModule {

    @Provides
    fun provideMoshi(): Moshi = MoshiBuilderProvider.moshiBuilder.build()

    @Provides
    @Singleton
    fun provideTypeConvert(
       moshi: Moshi
    ): RoomConverter = RoomConverter(moshi)
}
