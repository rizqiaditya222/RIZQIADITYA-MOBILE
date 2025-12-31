package com.kotlin.rizqiaditya.presentation.project

import com.kotlin.rizqiaditya.presentation.project.Skill
import java.io.File

data class ProjectFormState(
    val title: String = "",
    val description: String = "",
    val deploymentUrl: String = "",

    val github1Name: String = "",
    val github1Url: String = "",
    val github2Name: String = "",
    val github2Url: String = "",
    val github3Name: String = "",
    val github3Url: String = "",

    val selectedSkills: List<Skill> = emptyList(),
    val selectedFile: File? = null,
    val initialImageUrl: String? = null,

    val isSubmitting: Boolean = false,
    val submitError: String? = null,
    val success: Boolean = false,
    val createdProjectId: String? = null,
    val mismatchMessage: String? = null
)

