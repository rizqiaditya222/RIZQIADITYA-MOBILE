package com.kotlin.rizqiaditya.data.remote.api

import android.icu.text.CaseMap
import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectRequestDto
import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectResponseDto
import com.kotlin.rizqiaditya.data.remote.dto.story.StoryRequestDto
import com.kotlin.rizqiaditya.data.remote.dto.story.StoryResponseDto
import com.kotlin.rizqiaditya.data.remote.response.BaseResponse
import com.kotlin.rizqiaditya.data.remote.response.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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
        @Part("title") title: RequestBody?,
        @Part("githubRepos") githubRepos: RequestBody?,
        @Part("deploymentUrl") deploymentUrl: RequestBody?,
        @Part("techStack") techStack: RequestBody?,
    ): Response<BaseResponse<ProjectResponseDto>>

    @Multipart
    @PUT("projects/{id}")
    suspend fun editProject(
        @Path("id") id: String,
        @Part photo: MultipartBody.Part?,
        @Part("title") title: RequestBody?,
        @Part("githubRepos") githubRepos: RequestBody?,
        @Part("deploymentUrl") deploymentUrl: RequestBody?,
        @Part("techStack") techStack: RequestBody?,
    ): Response<BaseResponse<ProjectResponseDto>>

    @DELETE("projects/{id}")
    suspend fun deleteProject(
        @Path("id") id: String
    ): Response<BasicResponse>
}