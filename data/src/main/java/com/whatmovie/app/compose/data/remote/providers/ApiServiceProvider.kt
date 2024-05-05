package com.whatmovie.app.compose.data.remote.providers

import com.whatmovie.app.compose.data.remote.services.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
