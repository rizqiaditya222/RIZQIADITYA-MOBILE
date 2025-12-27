package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.project.ProjectResponseDto
import com.kotlin.rizqiaditya.domain.model.Project

fun ProjectResponseDto.toDomain(): Project {
    return Project(
        id = _id,
        photoUrl = photoUrl,
        title = title,
        githubRepos = githubRepos,
        deploymentUrl = deploymentUrl,
        techStack = techStack,
        createdAt = createdAt,
    )
}