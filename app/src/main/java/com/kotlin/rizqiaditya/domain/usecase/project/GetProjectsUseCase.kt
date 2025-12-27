package com.kotlin.rizqiaditya.domain.usecase.project

import com.kotlin.rizqiaditya.domain.model.Project
import com.kotlin.rizqiaditya.domain.repository.ProjectRepository
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(): List<Project> = repository.getProjects()
}

