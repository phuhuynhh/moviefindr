package com.whatmovie.app.compose.di.modules

import android.content.Context
import com.whatmovie.app.compose.domain.DispatchersProvider
import com.whatmovie.app.compose.util.DispatchersProviderImpl
import com.whatmovie.app.compose.data.remote.services.NetworkObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }

    @Provides
    fun provideNetworkObserver(@ApplicationContext context: Context): NetworkObserver {
        return NetworkObserver(context)
    }


}
