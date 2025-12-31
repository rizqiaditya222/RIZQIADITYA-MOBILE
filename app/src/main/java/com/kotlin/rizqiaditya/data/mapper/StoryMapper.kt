package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.story.StoryResponseDto
import com.kotlin.rizqiaditya.domain.model.Story

fun StoryResponseDto.toDomain(): Story {
    // Server may return either full URLs or relative paths (e.g. "uploads/xxx.jpg" or "/uploads/xxx.jpg").
    // Ensure we return a usable absolute URL for image loaders.
    val baseHost = "https://api.rizqiaditya.com"

    fun normalizePhotoUrl(photoUrl: String): String {
        val trimmed = photoUrl.trim()
        if (trimmed.isBlank()) return trimmed
        // Already a full URL -> normalize duplicate slashes after protocol
        if (trimmed.startsWith("http", ignoreCase = true)) {
            val parts = trimmed.split("://", limit = 2)
            if (parts.size < 2) return trimmed
            val protocol = parts[0]
            val rest = parts[1].replaceFirst("^/+".toRegex(), "")
            val raw = "$protocol://$rest"
            return raw.replace(" ", "%20")
        }

        // Path-like values -> ensure absolute URL using baseHost
        val path = if (trimmed.startsWith("/")) trimmed else "/$trimmed"
        // remove duplicate slashes between host and path
        val cleanedPath = path.replaceFirst("^/+".toRegex(), "/")
        val raw = baseHost.trimEnd('/') + cleanedPath
        return raw.replace(" ", "%20")
    }

    val fullPhotoUrl = normalizePhotoUrl(photoUrl)

    return Story(
        id = _id,
        photoUrl = fullPhotoUrl,
        caption = caption,
        location = location,
        isVisible = isVisible,
        expiredAt = expiredAt,
        comments = comments.map { it.toDomain() },
        createdAt = createdAt
    )
}