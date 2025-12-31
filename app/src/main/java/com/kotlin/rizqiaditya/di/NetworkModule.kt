package com.kotlin.rizqiaditya.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private val sharedClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun provideOkHttpClient(): OkHttpClient = sharedClient

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.rizqiaditya.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideStoryApi(retrofit: Retrofit) = retrofit.create(com.kotlin.rizqiaditya.data.remote.api.StoryApi::class.java)
    fun provideProjectApi(retrofit: Retrofit) = retrofit.create(com.kotlin.rizqiaditya.data.remote.api.ProjectApi::class.java)
    fun provideMessageApi(retrofit: Retrofit) = retrofit.create(com.kotlin.rizqiaditya.data.remote.api.MessageApi::class.java)
    fun provideCommentApi(retrofit: Retrofit) = retrofit.create(com.kotlin.rizqiaditya.data.remote.api.CommentApi::class.java)
    fun provideStatisticApi(retrofit: Retrofit) = retrofit.create(com.kotlin.rizqiaditya.data.remote.api.StatisticApi::class.java)
}