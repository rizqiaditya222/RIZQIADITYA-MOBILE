@file:Suppress("DEPRECATION")

package com.kotlin.rizqiaditya.presentation.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kotlin.rizqiaditya.presentation.components.common.ActionButton
import com.kotlin.rizqiaditya.presentation.components.common.LoadingDialog
import com.kotlin.rizqiaditya.presentation.components.common.MyOutlinedTextField
import com.kotlin.rizqiaditya.presentation.components.story.ImagePicker
import com.kotlin.rizqiaditya.presentation.components.project.GithubRepoSection
import com.kotlin.rizqiaditya.presentation.components.project.SkillCheckboxGrid
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectFormScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    factory: ViewModelProvider.Factory,
    isEdit: Boolean = false,
    projectId: String = ""
) {
    val viewModel: ProjectFormViewModel = viewModel(factory = factory)
    val state by viewModel.state.collectAsState()

    val showMismatch = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isEdit, projectId) {
        if (isEdit && projectId.isNotBlank()) {
            viewModel.onEvent(ProjectFormEvent.LoadForEdit(projectId))
        }
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        containerColor = MyBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isEdit) "Edit Project" else "Add Project",
                        style = MaterialTheme.typography.displaySmall,
                        color = MyWhite,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    ActionButton(
                        icon = Icons.Filled.Restore,
                        contentDescription = "Back",
                        onClick = { navController.popBackStack() }
                    )
                },
                actions = {
                    ActionButton(
                        icon = Icons.Filled.Send,
                        contentDescription = "Submit",
                        onClick = { viewModel.onEvent(ProjectFormEvent.Submit) }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MyBlack
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MyBlack)
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                ImagePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(216.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MyBaseGray)
                        .border(
                            width = 0.5.dp,
                            color = MyDarkGray,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    initialImageUrl = state.initialImageUrl,
                    onImageFile = { file -> viewModel.onEvent(ProjectFormEvent.ImageSelected(file)) }
                )
            }

            item {
                Column {
                    Text("Title", style = MaterialTheme.typography.titleMedium, color = MyWhite)
                    Spacer(Modifier.height(8.dp))
                    MyOutlinedTextField(value = state.title, onValueChange = { viewModel.onEvent(ProjectFormEvent.TitleChanged(it)) }, placeholder = "Project title", singleLine = true)
                }
            }

            item {
                Column {
                    Text("Description", style = MaterialTheme.typography.titleMedium, color = MyWhite)
                    Spacer(Modifier.height(8.dp))
                    MyOutlinedTextField(value = state.description, onValueChange = { viewModel.onEvent(ProjectFormEvent.DescriptionChanged(it)) }, placeholder = "Description for your project...", singleLine = false, maxLines = 5)
                }
            }

            item {
                Column {
                    Text("Deployment Url", style = MaterialTheme.typography.titleMedium, color = MyWhite)
                    Spacer(Modifier.height(8.dp))
                    MyOutlinedTextField(value = state.deploymentUrl, onValueChange = { viewModel.onEvent(ProjectFormEvent.DeploymentUrlChanged(it)) }, placeholder = "https://your-deployment.example", singleLine = true)
                }
            }

            item {
                Column {
                    Text("Github Url", style = MaterialTheme.typography.titleMedium, color = MyWhite)
                    Spacer(Modifier.height(8.dp))
                    GithubRepoSection(title = "Repository 1", isOptional = false, nameValue = state.github1Name, onNameChange = { viewModel.onEvent(ProjectFormEvent.GithubRepoChanged(0, it, state.github1Url)) }, urlValue = state.github1Url, onUrlChange = { viewModel.onEvent(ProjectFormEvent.GithubRepoChanged(0, state.github1Name, it)) })
                    Spacer(Modifier.height(12.dp))
                    GithubRepoSection(title = "Repository 2", isOptional = true, nameValue = state.github2Name, onNameChange = { viewModel.onEvent(ProjectFormEvent.GithubRepoChanged(1, it, state.github2Url)) }, urlValue = state.github2Url, onUrlChange = { viewModel.onEvent(ProjectFormEvent.GithubRepoChanged(1, state.github2Name, it)) })
                    Spacer(Modifier.height(12.dp))
                    GithubRepoSection(title = "Repository 3", isOptional = true, nameValue = state.github3Name, onNameChange = { viewModel.onEvent(ProjectFormEvent.GithubRepoChanged(2, it, state.github3Url)) }, urlValue = state.github3Url, onUrlChange = { viewModel.onEvent(ProjectFormEvent.GithubRepoChanged(2, state.github3Name, it)) })
                }
            }

            item {
                Column {
                    Text("Tech Stack", style = MaterialTheme.typography.titleMedium, color = MyWhite)
                    Spacer(Modifier.height(8.dp))
                    SkillCheckboxGrid(selectedSkills = state.selectedSkills, onToggle = { skill, checked -> viewModel.onEvent(ProjectFormEvent.SkillToggled(skill, checked)) })
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }

        if (state.isSubmitting) {
            LoadingDialog(message = "Uploading project…")
        }

        state.mismatchMessage?.let { msg ->
            AlertDialog(onDismissRequest = { /* no-op */ }, confirmButton = { Button(onClick = { navController.popBackStack() }) { Text("OK") } }, title = { Text("Upload result") }, text = { Text(msg) })
        }

        state.submitError?.let { err ->
            LaunchedEffect(err) { /* could show a toast */ }
        }
    }
}
