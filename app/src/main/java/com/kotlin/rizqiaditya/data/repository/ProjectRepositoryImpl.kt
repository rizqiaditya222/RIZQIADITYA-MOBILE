package com.kotlin.rizqiaditya.data.repository

import android.util.Log
import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.ProjectApi
import com.kotlin.rizqiaditya.data.util.ImageUploadUtil
import com.kotlin.rizqiaditya.domain.model.Project
import com.kotlin.rizqiaditya.domain.repository.ProjectRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val MAX_UPLOAD_BYTES: Long = 1_000_000L

class ProjectRepositoryImpl constructor(
    private val projectApi: ProjectApi
) : ProjectRepository {

    override suspend fun createProject(
        photo: File,
        title: String,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String,
        description: String?
    ): Project {
        var uploadFile = photo
        var tempCreated = false

        if (uploadFile.length() > MAX_UPLOAD_BYTES) {
            val compressed = withContext(Dispatchers.Default) {
                ImageUploadUtil.ensureUnderMax(uploadFile, MAX_UPLOAD_BYTES)
            }
            if (compressed != null && compressed.absolutePath != uploadFile.absolutePath) {
                uploadFile = compressed
                tempCreated = true
            }
        }

        val photoPart: MultipartBody.Part = ImageUploadUtil.buildMultipartPart("photo", uploadFile)

        val parts = mutableMapOf<String, RequestBody>()

        parts["title"] = title.toRequestBody("text/plain".toMediaType())
        if (!deploymentUrl.isNullOrBlank()) {
            parts["deploymentUrl"] = deploymentUrl.toRequestBody("text/plain".toMediaType())
        }
        parts["techStack"] = techStackJson.toRequestBody("application/json".toMediaType())
        if (!description.isNullOrBlank()) {
            parts["description"] = description.toRequestBody("text/plain".toMediaType())
        }

        // Parse and send github repos using bracket notation only
        if (!githubReposJson.isNullOrBlank()) {
            try {
                val arr = JSONArray(githubReposJson)
                Log.i("ProjectRepository", "Parsed github repos array length=${arr.length()}")

                for (i in 0 until arr.length()) {
                    val obj = arr.optJSONObject(i) ?: JSONObject()
                    val repoName = obj.optString("repoName").ifEmpty {
                        obj.optString("name")
                    }.trim()
                    val repoUrl = obj.optString("repoUrl").ifEmpty {
                        obj.optString("url")
                    }.trim()

                    Log.i("ProjectRepository", "repo[$i] -> name='$repoName' url='$repoUrl'")

                    parts["githubRepos[$i][repoName]"] = repoName.toRequestBody("text/plain".toMediaType())
                    parts["githubRepos[$i][repoUrl]"] = repoUrl.toRequestBody("text/plain".toMediaType())
                }

                Log.i("ProjectRepository", "Sent ${arr.length()} github repos using bracket notation")
            } catch (e: Exception) {
                Log.w("ProjectRepository", "Failed parsing githubReposJson: ${e.message}")
            }
        }

        val response = projectApi.createProject(
            photo = photoPart,
            parts = parts,
            partsList = null
        )

        try {
            if (tempCreated && uploadFile.exists()) uploadFile.delete()
        } catch (_: Exception) {}

        return response.body()!!.data.toDomain()
    }

    override suspend fun editProject(
        id: String,
        photo: File?,
        title: String?,
        githubReposJson: String?,
        deploymentUrl: String?,
        techStackJson: String?,
        description: String?
    ): Project {
        var uploadFile: File? = null
        var tempCreated = false

        if (photo != null) {
            uploadFile = photo
            if (uploadFile.length() > MAX_UPLOAD_BYTES) {
                val compressed = withContext(Dispatchers.Default) {
                    ImageUploadUtil.ensureUnderMax(uploadFile, MAX_UPLOAD_BYTES)
                }
                if (compressed != null && compressed.absolutePath != uploadFile.absolutePath) {
                    uploadFile = compressed
                    tempCreated = true
                }
            }
        }

        val photoPart = uploadFile?.let { ImageUploadUtil.buildMultipartPart("photo", it) }

        val parts = mutableMapOf<String, RequestBody>()

        if (!title.isNullOrBlank()) {
            parts["title"] = title.toRequestBody("text/plain".toMediaType())
        }
        if (!deploymentUrl.isNullOrBlank()) {
            parts["deploymentUrl"] = deploymentUrl.toRequestBody("text/plain".toMediaType())
        }
        if (!techStackJson.isNullOrBlank()) {
            parts["techStack"] = techStackJson.toRequestBody("application/json".toMediaType())
        }
        if (!description.isNullOrBlank()) {
            parts["description"] = description.toRequestBody("text/plain".toMediaType())
        }

        // Parse and send github repos using bracket notation only
        if (!githubReposJson.isNullOrBlank()) {
            try {
                val arr = JSONArray(githubReposJson)
                Log.i("ProjectRepository", "Parsed github repos array length=${arr.length()}")

                for (i in 0 until arr.length()) {
                    val obj = arr.optJSONObject(i) ?: JSONObject()
                    val repoName = obj.optString("repoName").ifEmpty {
                        obj.optString("name")
                    }.trim()
                    val repoUrl = obj.optString("repoUrl").ifEmpty {
                        obj.optString("url")
                    }.trim()

                    Log.i("ProjectRepository", "repo[$i] -> name='$repoName' url='$repoUrl'")

                    parts["githubRepos[$i][repoName]"] = repoName.toRequestBody("text/plain".toMediaType())
                    parts["githubRepos[$i][repoUrl]"] = repoUrl.toRequestBody("text/plain".toMediaType())
                }

                Log.i("ProjectRepository", "Sent ${arr.length()} github repos using bracket notation")
            } catch (e: Exception) {
                Log.w("ProjectRepository", "Failed parsing githubReposJson: ${e.message}")
            }
        }

        val response = projectApi.editProject(
            id = id,
            photo = photoPart,
            parts = parts,
            partsList = null
        )

        try {
            if (tempCreated && uploadFile?.exists() == true) uploadFile.delete()
        } catch (_: Exception) {}

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