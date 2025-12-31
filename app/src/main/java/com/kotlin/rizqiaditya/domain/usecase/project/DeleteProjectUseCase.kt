package com.kotlin.rizqiaditya.domain.usecase.project

import com.kotlin.rizqiaditya.domain.repository.ProjectRepository

class DeleteProjectUseCase constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteProject(id)
}
