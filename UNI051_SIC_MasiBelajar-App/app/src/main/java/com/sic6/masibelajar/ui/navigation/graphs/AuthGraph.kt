package com.sic6.masibelajar.ui.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sic6.masibelajar.ui.screens.auth.SignInScreen
import com.sic6.masibelajar.ui.screens.auth.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class AuthGraph {

    @Serializable
    data object SignIn : AuthGraph()

    @Serializable
    data object SignUp : AuthGraph()

    fun NavGraphBuilder.authGraph(navController: NavHostController) {
        navigation<RootGraph.Auth>(startDestination = SignIn) {
            composable<SignIn> { SignInScreen(navController) }
            composable<SignUp> { SignUpScreen(navController) }
        }
    }
}