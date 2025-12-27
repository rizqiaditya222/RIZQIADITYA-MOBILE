package com.kotlin.rizqiaditya.domain.model

data class Story(
    val id: String,
    val photoUrl: String,
    val caption: String,
    val location: String,
    val isVisible: Boolean,
    val expiredAt: String,
    val comments: List<Comment>,
    val createdAt: String
)
