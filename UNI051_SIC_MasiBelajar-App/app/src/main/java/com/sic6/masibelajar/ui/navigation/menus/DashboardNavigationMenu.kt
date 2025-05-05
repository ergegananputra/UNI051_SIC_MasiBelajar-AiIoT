package com.sic6.masibelajar.ui.navigation.menus

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.sic6.masibelajar.ui.navigation.graphs.DashboardGraph

sealed class DashboardNavigationMenu(val name: String, val route: DashboardGraph, val icon: ImageVector, val iconSelected: ImageVector) {

    data object Home : DashboardNavigationMenu("Home", DashboardGraph.Home, Icons.Outlined.Home, Icons.Rounded.Home)
    data object Smart : DashboardNavigationMenu("Smart", DashboardGraph.Smart, Icons.Default.SentimentSatisfied, Icons.Rounded.SentimentSatisfied)
    data object Article : DashboardNavigationMenu("History", DashboardGraph.History, Icons.Outlined.History, Icons.Filled.History)
    data object Setting : DashboardNavigationMenu("Setting", DashboardGraph.Setting, Icons.Outlined.Settings, Icons.Rounded.Settings)

    companion object {
        val menus = listOf(Home, Smart, Article, Setting)
    }
}