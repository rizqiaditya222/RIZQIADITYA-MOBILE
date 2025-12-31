package com.kotlin.rizqiaditya.presentation.story

import com.kotlin.rizqiaditya.domain.model.Comment
import com.kotlin.rizqiaditya.domain.model.Story

data class StoryUiState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val error: String? = null,

    val selectedImagePath: String? = null,
    val caption: String = "",
    val location: String = "",
    val isSubmitting: Boolean = false,
    val submitError: String? = null,
    val createdStoryId: String? = null
)
