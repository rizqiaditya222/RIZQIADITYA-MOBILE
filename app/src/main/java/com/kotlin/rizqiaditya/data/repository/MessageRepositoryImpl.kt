package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.MessageApi
import com.kotlin.rizqiaditya.data.remote.dto.message.MessageRequestDto
import com.kotlin.rizqiaditya.domain.model.Message
import com.kotlin.rizqiaditya.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageApi: MessageApi
) : MessageRepository {
    override suspend fun getMessages(): List<Message> {
        val response = messageApi.getMessages()
        val data = response.body()?.data
        // API returns a single message DTO in BaseResponse for this endpoint, convert to list
        return if (data == null) emptyList() else listOf(data.toDomain())
    }

    override suspend fun sendMessage(message: String) {
        messageApi.sendMessage(MessageRequestDto(message = message))
    }
}
