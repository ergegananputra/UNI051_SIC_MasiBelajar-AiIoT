package com.sic6.masibelajar.ui.screens.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.ui.navigation.graphs.DashboardGraph
import com.sic6.masibelajar.ui.screens.dashboard.components.AppBottomNavigation
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun DashboardScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        DashboardScreen(rememberNavController())
    }
}

@Composable
fun DashboardScreen(
    parentNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        DashboardGraph.DashboardNavHost(
            navController = navController,
            parentNavController = parentNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
