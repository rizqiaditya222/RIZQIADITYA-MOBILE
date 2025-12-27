package com.kotlin.rizqiaditya.domain.repository

import com.kotlin.rizqiaditya.domain.model.Message

interface MessageRepository {
    suspend fun getMessages(): List<Message>

    suspend fun sendMessage(message: String)
}
