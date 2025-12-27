package com.kotlin.rizqiaditya.data.remote.dto.comment

data class CommentResponseDto (
    val _id: String,
    val storyId: String,
    val comment: String,
    val createdAt: String
)
