package com.kotlin.rizqiaditya.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.domain.model.Comment
import com.kotlin.rizqiaditya.domain.usecase.comment.GetAllCommentsUseCase
import com.kotlin.rizqiaditya.domain.usecase.comment.GetCommentsByStoryIdUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.CreateStoryUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.GetStoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class StoryViewModel(
    private val createStoryUseCase: CreateStoryUseCase,
    private val getAllCommentsUseCase: GetAllCommentsUseCase,
    private val getCommentsByStoryIdUseCase: GetCommentsByStoryIdUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StoryUiState())
    val uiState: StateFlow<StoryUiState> = _uiState

    fun onEvent(event: StoryEvent) {
        when (event) {
            is StoryEvent.DescriptionChanged -> _uiState.update { it.copy(caption = event.value) }
            is StoryEvent.ImageSelected -> _uiState.update { it.copy(selectedImagePath = event.file?.absolutePath) }
            is StoryEvent.Submit -> {
                viewModelScope.launch {
                    // validate there is a selected local file
                    val path = _uiState.value.selectedImagePath
                    if (path.isNullOrBlank()) {
                        _uiState.update { it.copy(submitError = "Please select a photo before submitting") }
                        return@launch
                    }

                    val file = File(path)
                    if (!file.exists() || !file.isFile) {
                        _uiState.update { it.copy(submitError = "Selected photo is not a valid file") }
                        return@launch
                    }

                    _uiState.update { it.copy(isSubmitting = true, submitError = null) }
                    try {
                        val created = createStoryUseCase(file, _uiState.value.caption, _uiState.value.location)
                        _uiState.update {
                            it.copy(
                                isSubmitting = false,
                                createdStoryId = created.id,
                                stories = listOf(created) + it.stories
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isSubmitting = false, submitError = e.message) }
                    }
                }
            }
            is StoryEvent.LoadForEdit -> loadActiveStory(event.id)
        }
    }

    fun createStory(photo: File?, caption: String, location: String) {
        // kept for compatibility but prefer onEvent(Submit)
        if (photo == null) {
            _uiState.update { it.copy(submitError = "Please select a photo before submitting") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submitError = null) }
            try {
                val created = createStoryUseCase(photo, caption, location)
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        createdStoryId = created.id,
                        stories = listOf(created) + it.stories
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSubmitting = false, submitError = e.message) }
            }
        }
    }

    fun loadAllComment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val comments: List<Comment> = getAllCommentsUseCase()
                _uiState.update { it.copy(isLoading = false, comments = comments) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun loadActiveStory(storyId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val stories: List<Story> = getStoriesUseCase()
                val active: Story? = stories.find { it.id == storyId } ?: stories.firstOrNull()
                if (active != null) {
                    val comments = getCommentsByStoryIdUseCase(active.id)
                    _uiState.update { it.copy(isLoading = false, stories = stories, comments = comments, caption = active.caption, selectedImagePath = active.photoUrl) }
                } else {
                    _uiState.update { it.copy(isLoading = false, stories = stories) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
