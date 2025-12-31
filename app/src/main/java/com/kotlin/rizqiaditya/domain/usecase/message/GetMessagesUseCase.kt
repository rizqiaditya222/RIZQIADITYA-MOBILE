package com.kotlin.rizqiaditya.domain.usecase.message

import com.kotlin.rizqiaditya.domain.model.Message
import com.kotlin.rizqiaditya.domain.repository.MessageRepository

class GetMessagesUseCase constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(): List<Message> = repository.getMessages()
}
