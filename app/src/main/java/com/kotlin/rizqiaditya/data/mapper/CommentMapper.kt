package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.comment.CommentResponseDto
import com.kotlin.rizqiaditya.domain.model.Comment

fun CommentResponseDto.toDomain(): Comment {
    return Comment(
        id = _id,
        storyId = storyId,
        comment = comment,
        createdAt = createdAt,
    )
}