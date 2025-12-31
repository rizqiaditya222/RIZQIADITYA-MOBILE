package com.kotlin.rizqiaditya.presentation.message

import com.kotlin.rizqiaditya.domain.model.Message

data class MessageUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String? = null
)
