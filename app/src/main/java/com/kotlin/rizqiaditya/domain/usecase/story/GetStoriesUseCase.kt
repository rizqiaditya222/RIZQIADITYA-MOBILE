package com.kotlin.rizqiaditya.domain.usecase.story

import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    suspend operator fun invoke(): List<Story> = repository.getStories()
}

