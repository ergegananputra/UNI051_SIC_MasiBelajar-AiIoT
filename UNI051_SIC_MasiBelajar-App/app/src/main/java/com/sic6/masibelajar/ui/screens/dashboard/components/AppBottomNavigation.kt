package com.sic6.masibelajar.ui.screens.dashboard.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sic6.masibelajar.ui.navigation.menus.DashboardNavigationMenu

@Composable
fun AppBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        DashboardNavigationMenu.menus.forEach { menu ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(menu.route::class) } == true
            NavigationBarItem(
                icon = { Icon(imageVector = if (selected) menu.iconSelected else menu.icon, contentDescription = menu.name) },
                label = { Text(menu.name) },
                selected = selected,
                onClick = {
                    navController.navigate(menu.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = modifier
            )
        }
    }
}
