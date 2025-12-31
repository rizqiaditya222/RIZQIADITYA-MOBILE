package com.kotlin.rizqiaditya.presentation.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kotlin.rizqiaditya.presentation.components.common.TopBar
import com.kotlin.rizqiaditya.presentation.components.project.ProjectCard
import com.kotlin.rizqiaditya.presentation.navigation.NavRoute
import com.kotlin.rizqiaditya.ui.theme.MyBlack

@Composable
fun ProjectScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    parentNavController: NavHostController? = null,
    state: ProjectState = ProjectState(),
    onProjectClick: (String) -> Unit = {}
) {

    val appNav = parentNavController ?: navController

    val projects = if (state.projects.isEmpty()) {
        emptyList()
    } else {
        state.projects
    }

    Column(modifier = modifier
        .fillMaxSize()
        .background(color = MyBlack)
        .padding(horizontal = 24.dp)) {
        TopBar(
            title = "Project",
            leftIcon = Icons.Default.ArrowBackIos,
            onLeftClick = {  },
            rightIcon = Icons.Default.Add,
            onRightClick = { appNav.navigate(NavRoute.PROJECTFORM) }
        )
        ProjectCard(projects, onItemClick = onProjectClick)
    }
}
