package com.kotlin.rizqiaditya.domain.usecase.message

import com.kotlin.rizqiaditya.domain.model.Message
import com.kotlin.rizqiaditya.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(): List<Message> = repository.getMessages()
}

