package com.example.unsplashapicompose.api

import com.example.unsplashapicompose.BuildConfig
import com.example.unsplashapicompose.data.PaginationResponse
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {

    @GET("search/photos")
    suspend fun getUnsplashPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 0,
        @Query("per_page") per_page: Int = 10
    ): PaginationResponse<UnsplashPhoto>

    companion object {
        fun create(): UnsplashService {
            val logger =
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(AuthenticationInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashService::class.java)
        }
    }
}