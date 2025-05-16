package com.sic6.masibelajar.ui.screens.smart.camera

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.VideocamOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.domain.entities.VideoStreamRequest
import com.sic6.masibelajar.ui.components.Base64Image
import com.sic6.masibelajar.ui.screens.dashboard.VideoStreamViewModel
import com.sic6.masibelajar.ui.screens.smart.components.LabeledTextField

@Preview(showBackground = true)
@Composable
private fun CameraScreenPreview() {
    CameraScreen(
        navController = rememberNavController()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    videoStreamViewModel: VideoStreamViewModel = viewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val state by cameraViewModel.state.collectAsStateWithLifecycle()
    val response by videoStreamViewModel.response.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Camera Settings",
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            LabeledTextField(
                label = "Room Name",
                value = state.roomName,
                onValueChange = cameraViewModel::setRoomName,
                keyboardDecimals = false,
                modifier = Modifier.fillMaxWidth()
            )
            LabeledTextField(
                label = "IP Camera Input",
                value = state.ipCamera,
                onValueChange = cameraViewModel::setIpCamera,
                keyboardDecimals = false,
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (response != null) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        val containerWidth = constraints.maxWidth.toFloat()
                        val containerHeight = constraints.maxHeight.toFloat()

                        Base64Image(
                            base64String = response!!.data.frame,
                            modifier = Modifier.matchParentSize()
                        )

                        Canvas(modifier = Modifier.matchParentSize().scale(0.52f)) {
                            val path = Path().apply {
                                if (state.points.isNotEmpty()) {
                                    moveTo(state.points[0].x.toFloat() - 460, state.points[0].y.toFloat() - 290)
                                    for (i in 1 until state.points.size) {
                                        lineTo(state.points[i].x.toFloat() - 460, state.points[i].y.toFloat() - 290)
                                    }
                                    close()
                                }
                            }

                            drawPath(
                                path = path,
                                color = Color(0xFFFF0000),
                                style = Stroke(width = 40f)
                            )
                        }
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.VideocamOff,
                            tint = MaterialTheme.colorScheme.outlineVariant,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Video unavailable",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
            Column {
                Text(
                    text = "Number of Points (Minimum 3)",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { cameraViewModel.setPoint((state.numberOfPoints - 1).toString()) },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Remove a point"
                        )
                    }
                    OutlinedTextField(
                        value = state.numberOfPoints.toString(),
                        onValueChange = cameraViewModel::setPoint,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { cameraViewModel.setPoint((state.numberOfPoints + 1).toString()) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add a point"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Adjust Points",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )

            state.points.forEach { point ->
                PositionTextField(title = "Point ${point.id}") {
                    LabeledTextField(
                        label = "X Position",
                        value = point.x.toString(),
                        onValueChange = {
                            cameraViewModel.setPointX(
                                point.id,
                                it.toIntOrNull() ?: 0
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                    LabeledTextField(
                        label = "Y Position",
                        value = point.y.toString(),
                        onValueChange = {
                            cameraViewModel.setPointY(
                                point.id,
                                it.toIntOrNull() ?: 0
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun PositionTextField(
    title: String,
    modifier: Modifier = Modifier,
    textFields: @Composable (RowScope.() -> Unit)
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(modifier = Modifier.width(8.dp))
            HorizontalDivider()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            textFields()
        }
    }
}
