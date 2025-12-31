package com.kotlin.rizqiaditya.presentation.home

import com.kotlin.rizqiaditya.domain.model.Statistic
import com.kotlin.rizqiaditya.domain.model.Story

data class HomeState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val statistic: Statistic? = null,
    val errorMessage: String? = null
)
