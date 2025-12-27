package com.kotlin.rizqiaditya.data.remote.dto.story

import com.kotlin.rizqiaditya.data.remote.dto.comment.CommentResponseDto

data class StoryResponseDto(
    val _id: String,
    val photoUrl: String,
    val caption: String,
    val location: String,
    val isVisible: Boolean,
    val expiredAt: String,
    val comments: List<CommentResponseDto>,
    val createdAt: String
)
