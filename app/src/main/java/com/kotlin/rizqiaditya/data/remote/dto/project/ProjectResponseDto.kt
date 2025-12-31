package com.kotlin.rizqiaditya.data.remote.dto.project

data class ProjectResponseDto(
    val _id: String,
    val photoUrl: String,
    val title: String,
    val githubRepos: List<com.kotlin.rizqiaditya.data.remote.dto.project.GithubRepoDto>?,
    val deploymentUrl: String?,
    val techStack: List<String>,
    val description: String?,
    val createdAt: String
)
