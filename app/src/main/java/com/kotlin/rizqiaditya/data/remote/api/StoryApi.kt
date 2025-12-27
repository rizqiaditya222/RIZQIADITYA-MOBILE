package com.kotlin.rizqiaditya.data.remote.api

import com.kotlin.rizqiaditya.data.remote.dto.story.StoryResponseDto
import com.kotlin.rizqiaditya.data.remote.response.BaseResponse
import com.kotlin.rizqiaditya.data.remote.response.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoryApi {
    @GET("stories")
    suspend fun getStories(): Response<BaseResponse<List<StoryResponseDto>>>

    @GET("stories/archive")
    suspend fun getStoriesArchive(): Response<BaseResponse<StoryResponseDto>>

    @Multipart
    @POST("stories")
    suspend fun createStory(
        @Part photo: MultipartBody.Part?,
        @Part("caption") caption: RequestBody?,
        @Part("location") location: RequestBody?
    ): Response<BaseResponse<StoryResponseDto>>

    @DELETE("stories/{id}")
    suspend fun deleteStory(
        @Path("id") id: String
    ): Response<BasicResponse>
}