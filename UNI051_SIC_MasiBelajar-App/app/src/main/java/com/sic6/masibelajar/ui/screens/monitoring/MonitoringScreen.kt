package com.sic6.masibelajar.ui.screens.monitoring

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material.icons.outlined.HideImage
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.ScreenshotMonitor
import androidx.compose.material.icons.outlined.SlowMotionVideo
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.R
import com.sic6.masibelajar.ui.navigation.graphs.FeatureGraph
import com.sic6.masibelajar.ui.screens.components.CircleBackground
import com.sic6.masibelajar.ui.screens.monitoring.components.MenuButton
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme
import com.sic6.masibelajar.ui.theme.interFontFamily

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun MonitoringScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        MonitoringScreen(rememberNavController())
    }
}

@Composable
fun MonitoringScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val monitoringMenus = listOf(
        Triple("Switch", Icons.Outlined.PhotoCamera) {},
        Triple("Fullscreen", Icons.Outlined.Fullscreen) { navController.navigate(FeatureGraph.Fullscreen) },
        Triple("Screenshot", Icons.Outlined.ScreenshotMonitor) {},
        Triple("Playback", Icons.Outlined.SlowMotionVideo) {},
        Triple("History", Icons.Outlined.BrowseGallery) { navController.navigate(FeatureGraph.History) },
        Triple("Screen On", Icons.Outlined.Videocam) {},
        Triple("Boundary Off", Icons.Outlined.HideImage) {},
        Triple("Alarm", Icons.Outlined.NotificationsActive) {}
    )

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            // Video stream placeholder ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            ) {
                Text(
                    text = "CAM 1",
                    modifier = Modifier.padding(8.dp)
                )
            }
            // ---

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(monitoringMenus) { menu ->
                    MenuButton(
                        name = menu.first,
                        icon = menu.second,
                        onClick = menu.third
                    )
                }
            }

            ElevatedButton(
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Emergency Call",
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(R.drawable.img_emergency),
                        contentDescription = null,
                        modifier = Modifier
                            .size(72.dp)
                            .padding(top = 8.dp)
                    )
                }
            }
        }

        CircleBackground(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 360.dp)
                .zIndex(-1f)
        )
    }
}
