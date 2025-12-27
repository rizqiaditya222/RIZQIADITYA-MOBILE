package com.kotlin.rizqiaditya.data.remote.dto.project

import com.kotlin.rizqiaditya.data.remote.dto.project.GithubRepoDto

data class ProjectRequestDto(
    val _id: String,
    val photoUrl: String,
    val title: String,
    val githubRepos: List<GithubRepoDto>?,
    val deploymentUrl: String?,
    val techStack: List<String>,
)