package com.kotlin.rizqiaditya.domain.usecase.story

import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.domain.repository.StoryRepository
import java.io.File

class CreateStoryUseCase constructor(
    private val repository: StoryRepository
) {
    suspend operator fun invoke(
        photo: File,
        caption: String,
        location: String
    ): Story = repository.createStory(photo, caption, location)
}
