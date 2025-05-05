package com.sic6.masibelajar.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sic6.masibelajar.ui.navigation.graphs.AuthGraph.SignIn.authGraph
import com.sic6.masibelajar.ui.navigation.graphs.FeatureGraph.Fullscreen.featureGraph
import com.sic6.masibelajar.ui.screens.auth.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed class RootGraph {
    @Serializable
    data object Auth : RootGraph()

    @Serializable
    data object Main : RootGraph()

    companion object {
        @Composable
        fun RootNavHost(navController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
            NavHost(navController = navController, startDestination = if (viewModel.isLoggedIn()) Main else Auth) {
                authGraph(navController)
                featureGraph(navController)
            }
        }
    }
}