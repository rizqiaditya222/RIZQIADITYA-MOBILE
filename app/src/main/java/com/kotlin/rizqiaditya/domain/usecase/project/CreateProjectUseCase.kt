package com.kotlin.rizqiaditya.domain.usecase.project

import com.kotlin.rizqiaditya.domain.model.Project
import com.kotlin.rizqiaditya.domain.repository.ProjectRepository
import java.io.File
import javax.inject.Inject

class CreateProjectUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(
        photo: File,
        title: String,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String
    ): Project {
        return repository.createProject(
            photo = photo,
            title = title,
            githubReposJson = githubReposJson,
            deploymentUrl = deploymentUrl,
            techStackJson = techStackJson
        )
    }
}