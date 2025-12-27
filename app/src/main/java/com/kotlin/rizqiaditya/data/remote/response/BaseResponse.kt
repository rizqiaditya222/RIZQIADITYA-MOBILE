package com.kotlin.rizqiaditya.data.remote.response

data class BaseResponse<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: T
)
