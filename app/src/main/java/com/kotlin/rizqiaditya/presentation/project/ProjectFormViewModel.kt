package com.kotlin.rizqiaditya.presentation.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ProjectFormViewModel(
    private val createProjectUseCase: com.kotlin.rizqiaditya.domain.usecase.project.CreateProjectUseCase,
    private val editProjectUseCase: com.kotlin.rizqiaditya.domain.usecase.project.EditProjectUseCase,
    private val getProjectsUseCase: com.kotlin.rizqiaditya.domain.usecase.project.GetProjectsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectFormState())
    val state: StateFlow<ProjectFormState> = _state

    fun onEvent(event: ProjectFormEvent) {
        when (event) {
            is ProjectFormEvent.TitleChanged -> _state.update { it.copy(title = event.value) }
            is ProjectFormEvent.DescriptionChanged -> _state.update { it.copy(description = event.value) }
            is ProjectFormEvent.DeploymentUrlChanged -> _state.update { it.copy(deploymentUrl = event.value) }
            is ProjectFormEvent.ImageSelected -> _state.update { it.copy(selectedFile = event.file) }
            is ProjectFormEvent.GithubRepoChanged -> {
                val s = _state.value
                val names = arrayOf(s.github1Name, s.github2Name, s.github3Name)
                val urls = arrayOf(s.github1Url, s.github2Url, s.github3Url)
                if (event.index in 0..2) {
                    names[event.index] = event.name
                    urls[event.index] = event.url
                }
                _state.update {
                    it.copy(
                        github1Name = names[0], github1Url = urls[0],
                        github2Name = names[1], github2Url = urls[1],
                        github3Name = names[2], github3Url = urls[2]
                    )
                }
            }
            is ProjectFormEvent.SkillToggled -> {
                val current = _state.value.selectedSkills.toMutableList()
                if (event.checked) {
                    if (!current.contains(event.skill)) current.add(event.skill)
                } else {
                    current.remove(event.skill)
                }
                _state.update { it.copy(selectedSkills = current) }
            }
            is ProjectFormEvent.LoadForEdit -> loadForEdit(event.projectId)
            is ProjectFormEvent.Submit -> submit()
        }
    }

    private fun loadForEdit(projectId: String) {
        viewModelScope.launch {
            try {
                val projects = getProjectsUseCase()
                val p = projects.find { it.id == projectId }
                p?.let { proj ->
                    val skills = proj.techStack.mapNotNull {
                        try { Skill.valueOf(it.trim().uppercase()) } catch (_: Exception) { null }
                    }
                    _state.update {
                        it.copy(
                            title = proj.title,
                            description = proj.description ?: proj.title,
                            deploymentUrl = proj.deploymentUrl ?: "",
                            initialImageUrl = proj.photoUrl,
                            selectedSkills = skills,
                            github1Name = proj.githubRepos?.getOrNull(0)?.repoName ?: "",
                            github1Url = proj.githubRepos?.getOrNull(0)?.repoUrl ?: "",
                            github2Name = proj.githubRepos?.getOrNull(1)?.repoName ?: "",
                            github2Url = proj.githubRepos?.getOrNull(1)?.repoUrl ?: "",
                            github3Name = proj.githubRepos?.getOrNull(2)?.repoName ?: "",
                            github3Url = proj.githubRepos?.getOrNull(2)?.repoUrl ?: ""
                        )
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun submit() {
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, submitError = null, success = false, mismatchMessage = null) }
            try {
                val s = _state.value

                val techJson = s.selectedSkills.joinToString(separator = ",", prefix = "[", postfix = "]") { skill ->
                    "\"${skill.name}\""
                }

                val arr = JSONArray()
                fun addIfValid(name: String, url: String) {
                    if (name.isNotBlank()) {
                        val obj = JSONObject()
                        obj.put("repoName", name)
                        obj.put("repoUrl", url)
                        arr.put(obj)
                    }
                }

                addIfValid(s.github1Name, s.github1Url)
                addIfValid(s.github2Name, s.github2Url)
                addIfValid(s.github3Name, s.github3Url)

                val githubJson = if (arr.length() == 0) null else arr.toString()

                val file = s.selectedFile
                if (file != null) {
                    val created = createProjectUseCase(file, s.title, githubJson, if (s.deploymentUrl.isBlank()) null else s.deploymentUrl, techJson, if (s.description.isBlank()) null else s.description)
                    val returnedCount = created.githubRepos?.size ?: 0
                    val sentGithubCount = arr.length()
                    if (sentGithubCount != returnedCount) {
                        _state.update { it.copy(isSubmitting = false, mismatchMessage = "Server returned $returnedCount repo(s) but you sent $sentGithubCount.") }
                    } else {
                        _state.update { it.copy(isSubmitting = false, success = true, createdProjectId = created.id) }
                    }
                } else {
                    try {
                        _state.update { it.copy(isSubmitting = false, success = true) }
                    } catch (e: Exception) {
                        _state.update { it.copy(isSubmitting = false, submitError = e.message) }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isSubmitting = false, submitError = e.message) }
            }
        }
    }
}
