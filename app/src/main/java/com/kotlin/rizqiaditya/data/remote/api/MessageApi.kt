package com.kotlin.rizqiaditya.data.remote.api

import com.kotlin.rizqiaditya.data.remote.dto.message.MessageRequestDto
import com.kotlin.rizqiaditya.data.remote.dto.message.MessageResponseDto
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

interface MessageApi {
    @GET("messages")
    suspend fun getMessages(): Response<BaseResponse<List<MessageResponseDto>>>

    @POST("message")
    suspend fun sendMessage(
        @Body body: MessageRequestDto
    ): Response<BaseResponse<MessageResponseDto>>
}