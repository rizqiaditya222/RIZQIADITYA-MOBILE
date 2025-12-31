package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.StoryApi
import com.kotlin.rizqiaditya.data.util.ImageUploadUtil
import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.domain.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private const val MAX_UPLOAD_BYTES: Long = 1_000_000L

class StoryRepositoryImpl constructor(
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
        var uploadFile = photo
        var tempCreated = false

        if (uploadFile.length() > MAX_UPLOAD_BYTES) {
            val compressed = withContext(Dispatchers.Default) { ImageUploadUtil.ensureUnderMax(uploadFile, MAX_UPLOAD_BYTES) }
            if (compressed != null && compressed.absolutePath != uploadFile.absolutePath) {
                uploadFile = compressed
                tempCreated = true
            }
        }

        val photoPart: MultipartBody.Part = ImageUploadUtil.buildMultipartPart("photo", uploadFile)
        val captionPart = caption.toRequestBody("text/plain".toMediaType())
        val locationPart = location.toRequestBody("text/plain".toMediaType())

        var response = storyApi.createStory(photo = photoPart, caption = captionPart, location = locationPart)

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.success) {
                val result = body.data.toDomain()
                try { if (tempCreated && uploadFile.exists()) uploadFile.delete() } catch (_: Exception) {}
                return result
            } else {
                val msg = body?.message ?: "Unknown server error"
                if (msg.contains("Only image files are allowed", ignoreCase = true)) {
                    val recompressed = ImageUploadUtil.recompressToJpegInSameDir(photo)
                    if (recompressed != null) {
                        response = storyApi.createStory(photo = ImageUploadUtil.buildMultipartPart("photo", recompressed), caption = captionPart, location = locationPart)
                        if (response.isSuccessful) {
                            val b2 = response.body()
                            if (b2 != null && b2.success) {
                                val result2 = b2.data.toDomain()
                                try { if (recompressed.exists()) recompressed.delete() } catch (_: Exception) {}
                                try { if (tempCreated && uploadFile.exists()) uploadFile.delete() } catch (_: Exception) {}
                                return result2
                            }
                        }
                    }
                }
                throw IllegalStateException(msg)
            }
        } else {
            val errMsg = try { response.errorBody()?.string() } catch (_: Exception) { null } ?: response.message()
            if (errMsg != null && errMsg.contains("Only image files are allowed", ignoreCase = true)) {
                val recompressed = ImageUploadUtil.recompressToJpegInSameDir(photo)
                if (recompressed != null) {
                    response = storyApi.createStory(photo = ImageUploadUtil.buildMultipartPart("photo", recompressed), caption = captionPart, location = locationPart)
                    if (response.isSuccessful) {
                        val b2 = response.body()
                        if (b2 != null && b2.success) return b2.data.toDomain()
                    }
                }
            }
            try { if (tempCreated && uploadFile.exists()) uploadFile.delete() } catch (_: Exception) {}
            throw IllegalStateException("Upload failed: HTTP ${response.code()} - $errMsg")
        }
    }

    override suspend fun getStoriesArchive(): Story? {
        val response = storyApi.getStoriesArchive()
        return response.body()?.data?.toDomain()
    }

    override suspend fun deleteStory(id: String) {
        storyApi.deleteStory(id)
    }
}
