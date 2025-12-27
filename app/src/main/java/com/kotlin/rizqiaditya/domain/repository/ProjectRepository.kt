package com.kotlin.rizqiaditya.domain.repository

import com.kotlin.rizqiaditya.domain.model.Project
import java.io.File

interface ProjectRepository {
    suspend fun getProjects(): List<Project>

    suspend fun getStoriesArchive(): List<Project>

    suspend fun createProject(
        photo: File,
        title: String,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String
    ): Project

    suspend fun editProject(
        id: String,
        photo: File?,
        title: String?,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String?
    ): Project

    suspend fun deleteProject(id: String)
}
