package com.sic6.masibelajar.ui.screens.smart.camera

import com.sic6.masibelajar.domain.entities.Point

data class CameraScreenState(
    val roomName : String = "",
    val ipCamera : String = "",
    val timeThreshold : Int = 5,
    val points : List<Point> = listOf(
        Point(1, 0, 0),
        Point(2, 0, 0),
        Point(3, 0, 0),
    ),
    val targetClass: List<String> = listOf("Toddler", "Non-toddler")
) {
    val numberOfPoints: Int
        get() = points.size
}
