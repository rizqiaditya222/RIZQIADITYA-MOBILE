package com.kotlin.rizqiaditya.presentation.project

import com.kotlin.rizqiaditya.domain.model.Project

data class ProjectState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val errorMessage: String? = null
)

