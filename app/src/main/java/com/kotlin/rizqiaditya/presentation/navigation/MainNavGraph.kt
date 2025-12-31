package com.kotlin.rizqiaditya.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlin.rizqiaditya.presentation.components.common.AppBottomBar
import com.kotlin.rizqiaditya.presentation.home.HomeScreen
import com.kotlin.rizqiaditya.presentation.home.HomeViewModel
import com.kotlin.rizqiaditya.presentation.main.MainViewModel
import com.kotlin.rizqiaditya.presentation.project.ProjectScreen
import com.kotlin.rizqiaditya.presentation.project.ProjectViewModel
import com.kotlin.rizqiaditya.presentation.story.StoryScreen
import com.kotlin.rizqiaditya.presentation.navigation.NavRoute
import com.kotlin.rizqiaditya.ui.theme.MyBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavGraph(rootNavController: NavHostController, factory: ViewModelProvider.Factory) {
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val state = mainViewModel.state.collectAsState().value
    val navController = rememberNavController()

    Scaffold(
        containerColor = MyBlack,
        bottomBar = {
            AppBottomBar(
                navController = navController,
                currentDestination = state.currentDestination,
                onDestinationChanged = { mainViewModel.setDestination(it) }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoute.HOME) {
                val homeViewModel: HomeViewModel = viewModel(factory = factory)
                val homeState = homeViewModel.state.collectAsState().value
                val projectViewModel: ProjectViewModel = viewModel(factory = factory)
                val projectState = projectViewModel.state.collectAsState().value

                HomeScreen(
                    Modifier,
                    homeState,
                    projectState.projects.take(3),
                    navController,
                    rootNavController,
                    onProjectClick = { id: String -> rootNavController.navigate(NavRoute.projectFormRoute(isEdit = true, id = id)) },
                    onStoryClick = { id: String -> rootNavController.navigate(NavRoute.storyFormRoute(isEdit = true, id = id)) }
                )
            }
            composable(NavRoute.STORY) {
                StoryScreen(Modifier,
                    navController,
                    rootNavController,
                    factory)
            }
            composable(NavRoute.PROJECT) {
                val projectViewModel: ProjectViewModel = viewModel(factory = factory)
                val projectState = projectViewModel.state.collectAsState().value
                ProjectScreen(Modifier,
                    navController,
                    rootNavController,
                    state = projectState,
                    onProjectClick = { id: String -> rootNavController.navigate(NavRoute.projectFormRoute(isEdit = true, id = id)) }
                )
            }
        }
    }
}
