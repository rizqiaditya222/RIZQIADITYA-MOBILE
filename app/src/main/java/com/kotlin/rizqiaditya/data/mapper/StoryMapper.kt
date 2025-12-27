package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.story.StoryResponseDto
import com.kotlin.rizqiaditya.domain.model.Story

fun StoryResponseDto.toDomain(): Story {
    return Story(
        id = _id,
        photoUrl = photoUrl,
        caption = caption,
        location = location,
        isVisible = isVisible,
        expiredAt = expiredAt,
        comments = comments.map { it.toDomain() },
        createdAt = createdAt
    )
}