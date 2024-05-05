package com.whatmovie.app.compose.data.remote.authenticators

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import timber.log.Timber


class AuthorizationParamInterceptor(private val apiToken: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.newBuilder().addQueryParameter("api_key", this.apiToken).build()

        val request: Request = original
            .newBuilder()
            .url(url)
            .method(
                original.method,
                original.body
            ).build()


        try {
            val response = chain.proceed(request)
            return response
        } catch (ex: IOException) {
            Timber.e(ex)
            ex.printStackTrace()
            return chain.proceed(chain.request())
        }
    }
}