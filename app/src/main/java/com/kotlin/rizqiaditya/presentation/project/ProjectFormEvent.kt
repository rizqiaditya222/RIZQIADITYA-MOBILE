package com.kotlin.rizqiaditya.presentation.project

import java.io.File

sealed class ProjectFormEvent {
    data class TitleChanged(val value: String) : ProjectFormEvent()
    data class DescriptionChanged(val value: String) : ProjectFormEvent()
    data class DeploymentUrlChanged(val value: String) : ProjectFormEvent()

    data class GithubRepoChanged(val index: Int, val name: String, val url: String) : ProjectFormEvent()

    data class SkillToggled(val skill: Skill, val checked: Boolean) : ProjectFormEvent()

    data class ImageSelected(val file: File?) : ProjectFormEvent()

    object Submit : ProjectFormEvent()
    data class LoadForEdit(val projectId: String) : ProjectFormEvent()
}

