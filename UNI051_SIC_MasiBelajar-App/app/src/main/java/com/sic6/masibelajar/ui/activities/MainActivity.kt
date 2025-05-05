package com.sic6.masibelajar.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.ui.navigation.graphs.RootGraph
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasiBelajarDashboardTheme {
                Surface {
                    val navController = rememberNavController()
                    RootGraph.RootNavHost(navController)
                }
            }
        }
    }
}
