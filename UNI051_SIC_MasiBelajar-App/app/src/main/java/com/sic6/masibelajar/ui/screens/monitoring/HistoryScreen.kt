package com.sic6.masibelajar.ui.screens.monitoring

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sic6.masibelajar.domain.enums.EventType
import com.sic6.masibelajar.ui.screens.dashboard.VideoStreamViewModel
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Composable
private fun HistoryScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        HistoryScreen()
    }
}
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoStreamViewModel = viewModel()
) {
    val history by viewModel.history.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "History",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "View past activities and stay updated on your security",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy")),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
            )
        }

        Spacer(Modifier.height(12.dp))

        history.forEach { hist ->
            HistoryItem(
                icon = when (hist.type) {
                    EventType.FALL -> Icons.AutoMirrored.Filled.DirectionsRun
                    EventType.MISSING -> Icons.Default.QuestionMark
                    else -> Icons.Default.Person
                },
                description = hist.name,
                time = hist.time.split(" ")[1],
                color = when (hist.type) {
                    EventType.FALL, EventType.MISSING -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.primary
                }
            )
        }
    }
}

@Composable
fun HistoryItem(
    icon: ImageVector,
    description: String,
    time: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .padding(end = 12.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = description,
                color = color,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
