package com.sic6.masibelajar.ui.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.sic6.masibelajar.R
import com.sic6.masibelajar.ui.navigation.graphs.AuthGraph
import com.sic6.masibelajar.ui.navigation.graphs.RootGraph
import com.sic6.masibelajar.ui.screens.auth.UserViewModel
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme

@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Composable
private fun SettingScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        val navController = rememberNavController()
        SettingScreen(navController = navController)
    }
}

@Composable
fun SettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsState()
    val isDialogOpen = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 4.dp)
        )

        SectionTitle(title = "Profile")
        ProfileSection(email)

        SectionTitle(
            title = "Account Settings",
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        SettingItem(icon = Icons.Outlined.Person, title = "Account") { }
        SettingItem(icon = Icons.Outlined.WorkOutline, title = "Password & Security") { }
        SettingItem(icon = Icons.Outlined.Notifications, title = "Notification") { }
        SettingItem(icon = Icons.Outlined.Translate, title = "Languages Preferences") { }

        SectionTitle(
            title = "Other",
            modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
        )
        SettingItem(icon = Icons.AutoMirrored.Default.HelpOutline, title = "Help") { }
        SettingItem(
            icon = Icons.AutoMirrored.Default.Logout,
            title = "Logout"
        ) {
            isDialogOpen.value = true
        }

    }

    if (isDialogOpen.value) {
        LogoutDialog(
            onDismiss = { isDialogOpen.value = false },
            onConfirm = {
                isDialogOpen.value = false
                viewModel.logoutUser()
                navController.navigate(RootGraph.Auth) {
                    popUpTo(RootGraph.Main) { inclusive = true }
                }
            },
        )
    }
}

@Composable
fun ProfileSection(email: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = email.firstOrNull()?.uppercase() ?: "",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Column {
            Text(
                text = email.substringBefore("@"),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = email,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.outline,
        modifier = modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun SettingItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                    .size(20.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.padding(end = 20.dp)
        )
    }
}

@Composable
fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = "Log out",
                color = MaterialTheme.colorScheme.error,
            )
        },
        text = { Text("Logout from Lokari?") },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss,) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
            ) {
                Text(text = "Logout")
            }
        }
    )
}
