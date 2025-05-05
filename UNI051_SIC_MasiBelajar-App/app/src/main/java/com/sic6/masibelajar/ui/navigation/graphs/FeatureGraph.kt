package com.sic6.masibelajar.ui.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.sic6.masibelajar.domain.entities.Article
import com.sic6.masibelajar.ui.screens.article.ReadingScreen
import com.sic6.masibelajar.ui.screens.dashboard.DashboardScreen
import com.sic6.masibelajar.ui.screens.monitoring.CameraFullScreen
import com.sic6.masibelajar.ui.screens.monitoring.HistoryScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class FeatureGraph {

    @Serializable
    data object Dashboard : FeatureGraph()

    @Serializable
    data object History : FeatureGraph()

    @Serializable
    data object Fullscreen : FeatureGraph()

    @Serializable
    data class Read(
        val title: String,
        val author: String,
        val date: String,
        val content: String,
    ) : FeatureGraph()

    fun NavGraphBuilder.featureGraph(navController: NavHostController) {
        navigation<RootGraph.Main>(startDestination = Dashboard) {
            composable<Dashboard> { DashboardScreen(navController) }
            composable<Fullscreen> { CameraFullScreen() }
            composable<History> {
                HistoryScreen()
            }
            composable<Read> {
                val args = it.toRoute<Read>()
                ReadingScreen(
                    article = Article(args.title, args.author, args.date, args.content),
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}