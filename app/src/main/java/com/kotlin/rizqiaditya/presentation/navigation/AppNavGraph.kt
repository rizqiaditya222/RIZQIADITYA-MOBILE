package com.kotlin.rizqiaditya.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.kotlin.rizqiaditya.presentation.message.MessageScreen
import com.kotlin.rizqiaditya.presentation.project.ProjectFormScreen
import com.kotlin.rizqiaditya.presentation.splash.SplashScreen
import com.kotlin.rizqiaditya.presentation.story.StoryFormScreen

@Composable
fun AppNavGraph(factory: ViewModelProvider.Factory) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.MAIN
    ) {
        composable(NavRoute.SPLASH) {
            SplashScreen(
                navController = navController,
                navigateToMain = {
                    navController.navigate(NavRoute.MAIN) {
                        popUpTo(NavRoute.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoute.MAIN) {
            MainNavGraph(rootNavController = navController, factory = factory)
        }

        composable(NavRoute.MESSAGE) {
            MessageScreen(navController = navController, factory = factory)
        }

        // Simple no-arg routes so callers can navigate to forms without params
        composable(NavRoute.PROJECTFORM) {
            ProjectFormScreen(navController = navController, factory = factory, isEdit = false, projectId = "")
        }

        composable(NavRoute.STORYFORM) {
            StoryFormScreen(onBack = { navController.popBackStack() }, factory = factory, isEdit = false, storyId = "")
        }

        // Project form with query params isEdit (Bool) and id (String)
        composable(
            route = NavRoute.PROJECTFORM_PATTERN,
            arguments = listOf(
                navArgument("isEdit") { type = NavType.BoolType; defaultValue = false },
                navArgument("id") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ProjectFormScreen(navController = navController, factory = factory, isEdit = isEdit, projectId = id)
        }

        // Story form with query params
        composable(
            route = NavRoute.STORYFORM_PATTERN,
            arguments = listOf(
                navArgument("isEdit") { type = NavType.BoolType; defaultValue = false },
                navArgument("id") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false
            val id = backStackEntry.arguments?.getString("id") ?: ""
            StoryFormScreen(onBack = { navController.popBackStack() }, factory = factory, isEdit = isEdit, storyId = id)
        }
    }
}
