package com.sic6.masibelajar.ui.screens.home

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.R
import com.sic6.masibelajar.data.local.PrefManager
import com.sic6.masibelajar.domain.enums.EventType
import com.sic6.masibelajar.ui.components.Base64Image
import com.sic6.masibelajar.ui.screens.dashboard.VideoStreamViewModel
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun HomeScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        HomeScreen(viewModel = viewModel(), navController = rememberNavController())
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoStreamViewModel = viewModel(),
    navController: NavHostController,
) {
    val response by viewModel.response.collectAsState()
    val history by viewModel.history.collectAsState()
    val activeWarning = remember { mutableStateOf(EventType.NONE) }
    val showShareDialog = remember { mutableStateOf(false) }
    val color = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.error,
    ).random()
    val color1 = MaterialTheme.colorScheme.error
    val sharedUserEmails = remember { mutableStateListOf<Pair<String, Color>>(
        Pair("You", color1),
    ) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val initial = remember { mutableStateOf(true) }

    LaunchedEffect(history) {
        if (history.isNotEmpty()) {
            if (!initial.value) {
                activeWarning.value = history.last().type
                showNotification(activeWarning.value, context)
            } else {
                initial.value = false
            }
        }
    }

    NotificationPermissionRequest()

    if (activeWarning.value == EventType.FALL) {
        AlertDialogComponent(
            title = "URGENT!",
            body = "Fall detected! Please check immediately!",
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            confirmText = "Exit",
            onDismissRequest = { activeWarning.value = EventType.NONE }
        )
    } else if (activeWarning.value == EventType.MISSING) {
        AlertDialogComponent(
            title = "WARNING!",
            body = "Person detected in the Safezone for an extended time",
            icon = Icons.Rounded.Error,
            confirmText = "Exit",
            onDismissRequest = { activeWarning.value = EventType.NONE }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
    ) {
        // Title
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Now watching",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = PrefManager(navController.context).getRoomName(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "info",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        if (response != null) {
            response?.let { res ->
                Base64Image(
                    base64String = res.data.frame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

//            Text(
//                text = "Realtime ${res.data.results.timestamp}",
//                style = MaterialTheme.typography.labelMedium,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )

                Spacer(modifier = Modifier.height(16.dp))

                // Button actions
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ActionButton(
                        title = "Fullscreen",
                        icon = Icons.Default.Fullscreen,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        title = "Screenshot",
                        icon = Icons.Outlined.CameraAlt,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        title = "Playback",
                        icon = Icons.Default.SlowMotionVideo,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Inside
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VisitorCard(
                        title = "People Inside",
                        count = res.data.results.counts.inside,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Visitor Counts
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VisitorCard(
                        title = "Adult Visitor",
                        count = res.data.results.counts.non_toddler,
                        modifier = Modifier.weight(1f)
                    )
                    VisitorCard(
                        title = "Toddler Visitor",
                        count = res.data.results.counts.toddler,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
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
                CircularProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        SharedUsersSection(sharedUsers = sharedUserEmails) {
            showShareDialog.value = true
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (showShareDialog.value) {
            AddUserDialog(
                onDismiss = { showShareDialog.value = false },
                onAddUserSuccess = { username, email ->
                    sharedUserEmails.add(Pair(email, color))
                    showShareDialog.value = false
                }
            )
        }

    }
}

@Composable
fun ActionButton(title: String, icon: ImageVector, modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = { /* TODO: Handle click */ },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title)
        }
    }
}

@Composable
fun VisitorCard(title: String, count: Int, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = count.toString(), style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun SharedUsersSection(sharedUsers: List<Pair<String, Color>>, onAddUser: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Shared Users",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy((-8).dp)
            ) {
                sharedUsers.forEach { (email, color) ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = email.first().uppercase(),
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            IconButton(
                onClick = onAddUser,
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add User",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Composable
fun AlertDialogComponent(
    title: String,
    body: String,
    icon: ImageVector,
    confirmText: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(40.dp)
            )
        },
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = body,
                textAlign = TextAlign.Center,
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(
                onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = confirmText)
            }
        }
    )
}

@Composable
fun AddUserDialog(
    onDismiss: () -> Unit,
    onAddUserSuccess: (username: String, email: String) -> Unit
) {
    val emailState = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Shared User")
        },
        text = {
            Column {
                Text(text = "Enter user email")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    placeholder = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val email = emailState.value
                    if (email.isNotBlank()) {
                        onAddUserSuccess(email.substringBefore("@"), email)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun showNotification(type: EventType, context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val title = when (type) {
        EventType.FALL -> "Fall detected"
        EventType.MISSING -> "Missing detected"
        else -> ""
    }

    val message = when (type) {
        EventType.FALL -> "Fall detected! Please check immediately!"
        EventType.MISSING -> "Person detected in the Safezone for an extended time"
        else -> ""
    }

    val notification = NotificationCompat.Builder(context, "lokari_notification")
        .setSmallIcon(R.drawable.lokari_logo)
        .setContentTitle(title)
        .setContentText(message)
        .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    notificationManager.notify(1, notification)
}

@Composable
fun NotificationPermissionRequest() {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val shouldShowPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    LaunchedEffect(Unit) {
        if (shouldShowPermission && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
