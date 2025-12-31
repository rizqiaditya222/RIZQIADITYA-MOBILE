package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectResponseDto
import com.kotlin.rizqiaditya.domain.model.Project

fun ProjectResponseDto.toDomain(): Project {
    val baseHost = "https://api.rizqiaditya.com"

    fun normalizePhotoUrl(photoUrl: String): String {
        val trimmed = photoUrl.trim()
        if (trimmed.isBlank()) return trimmed
        if (trimmed.startsWith("http", ignoreCase = true)) {
            val parts = trimmed.split("://", limit = 2)
            if (parts.size < 2) return trimmed
            val protocol = parts[0]
            val rest = parts[1].replaceFirst("^/+".toRegex(), "")
            return "$protocol://$rest"
        }

        val path = if (trimmed.startsWith("/")) trimmed else "/$trimmed"
        val cleanedPath = path.replaceFirst("^/+".toRegex(), "/")
        return baseHost.trimEnd('/') + cleanedPath
    }

    return Project(
        id = _id,
        photoUrl = normalizePhotoUrl(photoUrl),
        title = title,
        githubRepos = githubRepos,
        deploymentUrl = deploymentUrl,
        techStack = techStack,
        description = description,
        createdAt = createdAt,
    )
}