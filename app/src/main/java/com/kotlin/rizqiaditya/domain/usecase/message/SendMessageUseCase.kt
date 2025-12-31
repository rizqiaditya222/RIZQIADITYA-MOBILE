package com.kotlin.rizqiaditya.domain.usecase.message

import com.kotlin.rizqiaditya.domain.repository.MessageRepository

class SendMessageUseCase constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(message: String) = repository.sendMessage(message)
}
