package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.message.MessageResponseDto
import com.kotlin.rizqiaditya.domain.model.Message

fun MessageResponseDto.toDomain(): Message {
    return Message (
        id = _id,
        message = message,
        createdAt = createdAt
    )
}