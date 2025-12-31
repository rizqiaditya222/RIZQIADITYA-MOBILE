package com.kotlin.rizqiaditya.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.rizqiaditya.domain.model.Statistic
import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.domain.usecase.statistic.GetTodayStatisticUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.GetStoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getTodayStatisticUseCase: GetTodayStatisticUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val stories: List<Story> = getStoriesUseCase()
                val stat: Statistic? = getTodayStatisticUseCase()
                _state.update { it.copy(isLoading = false, stories = stories, statistic = stat) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}
