package com.kotlin.rizqiaditya.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kotlin.rizqiaditya.presentation.components.home.StatisticsCard
import com.kotlin.rizqiaditya.presentation.components.story.StorySlider
import com.kotlin.rizqiaditya.presentation.components.project.ProjectCard
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import com.kotlin.rizqiaditya.domain.model.Project
import com.kotlin.rizqiaditya.presentation.navigation.NavRoute
import com.kotlin.rizqiaditya.domain.model.Story

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    latestProjects: List<Project> = emptyList(),
    navController: NavHostController,
    parentNavController: NavHostController? = null,
    onProjectClick: (String) -> Unit = {},
    onStoryClick: (String) -> Unit = {}
) {
    val appNav = parentNavController ?: navController
    val storyImages = remember(state) { state.stories.map { it.photoUrl } }

    Column(modifier = modifier
        .background(color = MyBlack)
        .fillMaxSize()
        .padding(horizontal = 24.dp)
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row (modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Halo Rizqi Aditya!", style = MaterialTheme.typography.displaySmall, color = MyWhite)
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Notification",
                    tint = MyWhite,
                    modifier = Modifier.clickable(onClick = { appNav.navigate(NavRoute.MESSAGE) })
                )
            }
            Spacer(Modifier.height(4.dp))
            Text("22 Desember 2025", style = MaterialTheme.typography.bodyLarge, color = MyDarkGray)
        }

        StatisticsCard(
            TrafficAmount = state.statistic?.todayVisit ?: 0,
            CommentsAmount = state.statistic?.todayComment ?: 0,
            MessagesAmount = state.statistic?.todayMessage ?: 0
        )

        Column (modifier = Modifier.fillMaxWidth()) {
            Row (modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Current Stories", style = MaterialTheme.typography.headlineSmall, color = MyWhite)
                Text("See All",
                    style = MaterialTheme.typography.titleLarge,
                    color = MyDarkGray,
                    modifier = Modifier.clickable(onClick = {navController.navigate(NavRoute.STORY)}))
            }
            Spacer(Modifier.height(8.dp))

            StorySlider(state.stories, onItemClick = onStoryClick)
        }

        Column (modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Latest Portofolio", style = MaterialTheme.typography.headlineSmall, color = MyWhite)
                Text("See All",
                    style = MaterialTheme.typography.titleLarge,
                    color = MyDarkGray,
                    modifier = Modifier.clickable(onClick = {navController.navigate(NavRoute.PROJECT)}))

            }

            Spacer(Modifier.height(8.dp))

            ProjectCard(latestProjects, onItemClick = onProjectClick)
        }

    }
}
