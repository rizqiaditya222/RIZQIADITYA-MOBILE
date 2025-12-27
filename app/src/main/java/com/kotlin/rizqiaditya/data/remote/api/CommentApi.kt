package com.kotlin.rizqiaditya.data.remote.api

import com.kotlin.rizqiaditya.data.remote.dto.comment.CommentRequestDto
import com.kotlin.rizqiaditya.data.remote.dto.comment.CommentResponseDto
import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectRequestDto
import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectResponseDto
import com.kotlin.rizqiaditya.data.remote.response.BaseResponse
import com.kotlin.rizqiaditya.data.remote.response.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface CommentApi {
    @GET("comments")
    suspend fun getAllComments(): Response<BaseResponse<List<CommentResponseDto>>>

    @POST("comments")
    suspend fun sendComment(
        @Body body: CommentRequestDto
    ): Response<BaseResponse<CommentResponseDto>>

    @GET("comments/story/{storyId}")
    suspend fun getCommentByStoryId(
        @Path("storyId") id: String
    ): Response<BaseResponse<List<CommentResponseDto>>>

    @DELETE("comments/{id}")
    suspend fun deleteComment(
        @Path("id") id: String
    ): Response<BasicResponse>
}