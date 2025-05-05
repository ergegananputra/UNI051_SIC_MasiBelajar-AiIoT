package com.sic6.masibelajar.ui.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun CircleBackground(modifier: Modifier = Modifier) {

    val topCircleFirstColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
    val topCircleSecondColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.2f)
    val bottomCircleColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f)

    Canvas(modifier = modifier.size(280.dp)) {
        drawCircle(
            color = topCircleFirstColor,
            radius = size.minDimension,
            center = center
        )
        drawCircle(
            color = topCircleSecondColor,
            radius = size.minDimension,
            center = center
        )
        drawCircle(
            color = bottomCircleColor,
            radius = size.minDimension,
            center = Offset(center.x, center.y + 152f)
        )
    }
}