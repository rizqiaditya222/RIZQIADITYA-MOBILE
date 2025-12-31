package com.kotlin.rizqiaditya.domain.repository

import com.kotlin.rizqiaditya.domain.model.Story
import java.io.File

interface StoryRepository {
    suspend fun getStories(): List<Story>

    suspend fun  createStory(
        photo: File,
        caption: String,
        location: String
    ): Story

    suspend fun getStoriesArchive(): Story?

    suspend fun deleteStory(id: String)
}
