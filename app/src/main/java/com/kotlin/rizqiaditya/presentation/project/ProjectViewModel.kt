package com.kotlin.rizqiaditya.presentation.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.rizqiaditya.domain.usecase.project.GetProjectsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val getProjectsUseCase: GetProjectsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProjectState())
    val state: StateFlow<ProjectState> = _state

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val projects = getProjectsUseCase()
                _state.update { it.copy(isLoading = false, projects = projects, errorMessage = null) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun deleteProject(id: String) {
    }
}
