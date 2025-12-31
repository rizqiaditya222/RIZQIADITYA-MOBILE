package com.kotlin.rizqiaditya.presentation.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.rizqiaditya.domain.usecase.message.GetMessagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessageViewModel(
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState

    init {
        loadMessages()
    }

    fun loadMessages(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val messages = getMessagesUseCase()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messages = messages,
                        error = null
                    )
                }

            }
            catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}