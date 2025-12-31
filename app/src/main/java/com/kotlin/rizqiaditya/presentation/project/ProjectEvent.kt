package com.kotlin.rizqiaditya.presentation.project

sealed class ProjectEvent {
    object LoadProjects : ProjectEvent()
    data class DeleteProject(val id: String) : ProjectEvent()
}

