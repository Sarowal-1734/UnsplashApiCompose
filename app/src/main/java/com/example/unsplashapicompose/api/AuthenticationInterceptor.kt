package com.example.unsplashapicompose.api

import com.example.unsplashapicompose.BuildConfig.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            val modifiedRequest = request.newBuilder()
                .addHeader("Accept-Version", "v1")
                .addHeader("Authorization", ACCESS_TOKEN)
                .build()
            chain.proceed(modifiedRequest)

        } catch (e: Exception) {
            chain.proceed(request)
        }
    }
}