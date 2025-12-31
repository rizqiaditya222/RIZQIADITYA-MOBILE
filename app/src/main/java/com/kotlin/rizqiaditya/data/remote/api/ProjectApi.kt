package com.kotlin.rizqiaditya.data.remote.api

import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectResponseDto
import com.kotlin.rizqiaditya.data.remote.response.BaseResponse
import com.kotlin.rizqiaditya.data.remote.response.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ProjectApi {

    @GET("projects")
    suspend fun getProject(): Response<BaseResponse<List<ProjectResponseDto>>>

    @GET("stories/archive")
    suspend fun getStoriesArchive(): Response<BaseResponse<List<ProjectResponseDto>>>

    @Multipart
    @POST("projects")
    suspend fun createProject(
        @Part photo: MultipartBody.Part,
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part partsList: List<MultipartBody.Part>?
    ): Response<BaseResponse<ProjectResponseDto>>

    @Multipart
    @PUT("projects/{id}")
    suspend fun editProject(
        @Path("id") id: String,
        @Part photo: MultipartBody.Part?,
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part partsList: List<MultipartBody.Part>?
    ): Response<BaseResponse<ProjectResponseDto>>

    @DELETE("projects/{id}")
    suspend fun deleteProject(
        @Path("id") id: String
    ): Response<BasicResponse>
}