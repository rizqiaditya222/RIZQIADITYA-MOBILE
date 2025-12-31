package com.kotlin.rizqiaditya.domain.model

import com.kotlin.rizqiaditya.data.remote.dto.project.GithubRepoDto

data class Project(
    val id: String,
    val photoUrl: String,
    val title: String,
    val githubRepos: List<GithubRepoDto>?,
    val deploymentUrl: String?,
    val techStack: List<String>,
    val description: String?,
    val createdAt: String
)
