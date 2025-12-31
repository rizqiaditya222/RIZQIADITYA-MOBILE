package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.MessageApi
import com.kotlin.rizqiaditya.data.remote.dto.message.MessageRequestDto
import com.kotlin.rizqiaditya.domain.model.Message
import com.kotlin.rizqiaditya.domain.repository.MessageRepository

class MessageRepositoryImpl constructor(
    private val messageApi: MessageApi
) : MessageRepository {
    override suspend fun getMessages(): List<Message> {
        val response = messageApi.getMessages()
        return response.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun sendMessage(message: String) {
        messageApi.sendMessage(MessageRequestDto(message = message))
    }
}
