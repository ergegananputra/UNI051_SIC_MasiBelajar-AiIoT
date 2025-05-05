package com.sic6.masibelajar.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sic6.masibelajar.ui.screens.dashboard.VideoStreamViewModel
import com.sic6.masibelajar.ui.screens.home.HomeScreen
import com.sic6.masibelajar.ui.screens.monitoring.HistoryScreen
import com.sic6.masibelajar.ui.screens.setting.SettingScreen
import com.sic6.masibelajar.ui.screens.smart.AlarmScreen
import com.sic6.masibelajar.ui.screens.smart.SmartSettingScreen
import com.sic6.masibelajar.ui.screens.smart.camera.CameraScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class DashboardGraph {

    @Serializable
    data object Home : DashboardGraph()

    @Serializable
    data object Smart : DashboardGraph()

    @Serializable
    data object Camera : DashboardGraph()

    @Serializable
    data object Alarm : DashboardGraph()

    @Serializable
    data object History : DashboardGraph()

    @Serializable
    data object Setting : DashboardGraph()

    companion object {
        @Composable
        fun DashboardNavHost(
            navController: NavHostController,
            parentNavController: NavHostController,
//            eventHandler : (DashboardGraph) -> Unit,
            modifier: Modifier = Modifier,
            viewModel: VideoStreamViewModel = hiltViewModel()
        ) {
            NavHost(
                navController = navController,
                startDestination = Home
            ) {
                composable<Home> {
                    HomeScreen(
                        navController = parentNavController,
                        viewModel = viewModel,
                        modifier = modifier
                    )
                }
                composable<Smart> {
                    SmartSettingScreen(
                        navController = navController,
                        modifier = modifier
                    )
                }
                composable<Camera> {
                    CameraScreen(
                        navController = navController,
                        videoStreamViewModel = viewModel,
                        modifier = modifier
                    )
                }
                composable<Alarm> {
                    AlarmScreen(
                        navController = navController,
                        videoStreamViewModel = viewModel,
                        modifier = modifier
                    )
                }
                composable<History> {
                    HistoryScreen(
                        viewModel = viewModel,
                        modifier = modifier
                    )
                }
                composable<Setting> {
                    SettingScreen(
                        modifier = modifier,
                        navController = parentNavController,
                    )
                }
            }
        }
    }
}