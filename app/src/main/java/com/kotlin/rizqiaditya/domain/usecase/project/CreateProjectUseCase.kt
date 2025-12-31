package com.kotlin.rizqiaditya.domain.usecase.project

import com.kotlin.rizqiaditya.domain.model.Project
import com.kotlin.rizqiaditya.domain.repository.ProjectRepository
import java.io.File

class CreateProjectUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(
        photo: File,
        title: String,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String,
        description: String?
    ): Project {
        return repository.createProject(
            photo = photo,
            title = title,
            githubReposJson = githubReposJson,
            deploymentUrl = deploymentUrl,
            techStackJson = techStackJson,
            description = description
        )
    }
}