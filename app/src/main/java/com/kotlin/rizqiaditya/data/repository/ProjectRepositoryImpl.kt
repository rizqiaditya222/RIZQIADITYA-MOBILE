package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.ProjectApi
import com.kotlin.rizqiaditya.domain.model.Project
import com.kotlin.rizqiaditya.domain.repository.ProjectRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectApi: ProjectApi
) : ProjectRepository {

    override suspend fun createProject(
        photo: File,
        title: String,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String
    ): Project {
        val photoPart = MultipartBody.Part.createFormData(
            name = "photo",
            filename = photo.name,
            body = photo.asRequestBody("image/*".toMediaType())
        )

        val titleBody = title.toRequestBody("text/plain".toMediaType())
        val githubBody = githubReposJson?.toRequestBody("application/json".toMediaType())
        val deploymentBody = deploymentUrl?.toRequestBody("text/plain".toMediaType())
        val techStackBody = techStackJson.toRequestBody("application/json".toMediaType())

        val response = projectApi.createProject(
            photo = photoPart,
            title = titleBody,
            githubRepos = githubBody,
            deploymentUrl = deploymentBody,
            techStack = techStackBody
        )

        return response.body()!!.data.toDomain()
    }

    override suspend fun editProject(
        id: String,
        photo: File?,
        title: String?,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String?
    ): Project {
        val photoPart = photo?.let {
            MultipartBody.Part.createFormData(
                name = "photo",
                filename = it.name,
                body = it.asRequestBody("image/*".toMediaType())
            )
        }

        val titleBody = title?.toRequestBody("text/plain".toMediaType())
        val githubBody = githubReposJson?.toRequestBody("application/json".toMediaType())
        val deploymentBody = deploymentUrl?.toRequestBody("text/plain".toMediaType())
        val techStackBody = techStackJson?.toRequestBody("application/json".toMediaType())

        val response = projectApi.editProject(
            id = id,
            photo = photoPart,
            title = titleBody,
            githubRepos = githubBody,
            deploymentUrl = deploymentBody,
            techStack = techStackBody
        )

        return response.body()!!.data.toDomain()
    }

    override suspend fun getProjects(): List<Project> {
        val response = projectApi.getProject()
        return response.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getStoriesArchive(): List<Project> {
        val response = projectApi.getStoriesArchive()
        return response.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun deleteProject(id: String) {
        projectApi.deleteProject(id)
    }
}
