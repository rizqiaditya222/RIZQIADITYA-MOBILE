package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.StoryApi
import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.domain.repository.StoryRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val storyApi: StoryApi
) : StoryRepository {
    override suspend fun getStories(): List<Story> {
        val response = storyApi.getStories()
        return response.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun createStory(
        photo: File,
        caption: String,
        location: String
    ): Story {
        val photoPart = photo?.let {
            MultipartBody.Part.createFormData(
                name = "photo",
                filename = it.name,
                body = it.asRequestBody("image/*".toMediaType())
            )
        }
        val captionPart = caption.toRequestBody("text/plain".toMediaType())
        val locationPart = location.toRequestBody("text/plain".toMediaType())

        val response = storyApi.createStory(
            photo = photoPart,
            caption = captionPart,
            location = locationPart)

        return response.body()!!.data.toDomain()
    }

    override suspend fun getStoriesArchive(): Story? {
        val response = storyApi.getStoriesArchive()
        return response.body()?.data?.toDomain()
    }

    override suspend fun deleteStory(id: String) {
        storyApi.deleteStory(id)
    }
}
