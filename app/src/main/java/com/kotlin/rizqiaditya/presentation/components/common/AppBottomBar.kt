package com.kotlin.rizqiaditya.presentation.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kotlin.rizqiaditya.presentation.navigation.MainDestination
import com.kotlin.rizqiaditya.presentation.navigation.NavRoute
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import com.kotlin.rizqiaditya.ui.theme.RizqiadityaTheme

@Composable
fun AppBottomBar(
    navController: NavController,
    currentDestination: MainDestination,
    onDestinationChanged: (MainDestination) -> Unit
) {
    val items = listOf(
        MainDestination.HOME,
        MainDestination.STORY,
        MainDestination.PROJECT
    )

    NavigationBar(containerColor = MyBlack,
        modifier = Modifier.padding(horizontal = 12.dp)) {
        items.forEach { destination ->
            val isSelected = currentDestination == destination

            val iconImage: ImageVector = when (destination) {
                MainDestination.HOME -> Icons.Filled.Home
                MainDestination.STORY -> Icons.Filled.Favorite
                MainDestination.PROJECT -> Icons.Filled.AddCircle
                else -> Icons.Filled.Home
            }

            val route = when (destination) {
                MainDestination.HOME -> NavRoute.HOME
                MainDestination.STORY -> NavRoute.STORY
                MainDestination.PROJECT -> NavRoute.PROJECT
                else -> NavRoute.HOME
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onDestinationChanged(destination)

                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(NavRoute.HOME) { inclusive = false }
                    }
                },
                icon = {
                    Icon(
                        imageVector = iconImage,
                        contentDescription = destination.name,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(destination.name) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MyWhite,
                    unselectedIconColor = MyDarkGray,
                    selectedTextColor = MyWhite,
                    unselectedTextColor = MyDarkGray,
                    indicatorColor = MyDarkGray.copy(alpha = 0f)
                ),
            )
        }
    }
}
