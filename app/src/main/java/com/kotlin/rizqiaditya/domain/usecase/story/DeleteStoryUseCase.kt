package com.kotlin.rizqiaditya.domain.usecase.story

import com.kotlin.rizqiaditya.domain.repository.StoryRepository

class DeleteStoryUseCase(
    private val repository: StoryRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteStory(id)
    }
}
