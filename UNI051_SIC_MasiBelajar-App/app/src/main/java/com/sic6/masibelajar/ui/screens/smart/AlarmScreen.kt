package com.sic6.masibelajar.ui.screens.smart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Face4
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.domain.entities.VideoStreamRequest
import com.sic6.masibelajar.ui.screens.dashboard.VideoStreamViewModel
import com.sic6.masibelajar.ui.screens.smart.camera.CameraViewModel
import com.sic6.masibelajar.ui.screens.smart.components.LabeledTextField

@Preview(showBackground = true)
@Composable
private fun AlarmScreenPreview() {
    AlarmScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    videoStreamViewModel: VideoStreamViewModel = viewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val state by cameraViewModel.state.collectAsStateWithLifecycle()
    var fallDetection by remember { mutableStateOf(false) }
    var safezoneDetection by remember { mutableStateOf(true) }
    val color = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.error,
    ).random()
    val color1 = MaterialTheme.colorScheme.error
    val sharedUserEmails = remember { mutableStateListOf<Pair<String, Color>>(
        Pair("You", color1),
    ) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alarm Config",
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            videoStreamViewModel.send(
                                VideoStreamRequest(
                                    id = "stream",
                                    points = cameraViewModel.state.value.points.map { point ->
                                        listOf(point.x, point.y)
                                    },
                                    preview = true,
                                    target_class = cameraViewModel.state.value.targetClass,
                                    time_threshold = cameraViewModel.state.value.timeThreshold,
                                    track = true,
                                    url = cameraViewModel.state.value.ipCamera
                                )
                            )
                            cameraViewModel.save()
                            Toast.makeText(context, "Configuration saved", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Save")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            LabeledTextField(
                label = "Notification Wait Time (seconds)",
                value = state.timeThreshold.toString(),
                onValueChange = { cameraViewModel.setTimeThreshold(it) },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Alarm Active",
                style = MaterialTheme.typography.titleMedium
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Fall Detection")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = fallDetection, onCheckedChange = { fallDetection = it })
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Safezone Detection")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = safezoneDetection, onCheckedChange = { safezoneDetection = it })
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Target Class",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        if (!state.targetClass.contains("toddler")) cameraViewModel.addTarget("toddler") else cameraViewModel.removeTarget("toddler")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.targetClass.contains("toddler")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = if (state.targetClass.contains("toddler")) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.ChildCare, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Toddler")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (!state.targetClass.contains("non-toddler")) cameraViewModel.addTarget("non-toddler") else cameraViewModel.removeTarget("non-toddler")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.targetClass.contains("non-toddler")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = if (state.targetClass.contains("non-toddler")) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Face4, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Non-toddler")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Share Notification",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                sharedUserEmails.forEach { (name, color) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = name.first().uppercase(),
                                color = MaterialTheme.colorScheme.surface,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(name, fontSize = 12.sp)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add User",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
