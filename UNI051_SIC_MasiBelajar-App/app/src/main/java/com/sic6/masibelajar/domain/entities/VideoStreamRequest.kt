package com.sic6.masibelajar.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class VideoStreamRequest(
    val id: String,
    val points: List<List<Int>>,
    val preview: Boolean,
    val target_class: List<String>,
    val time_threshold: Int,
    val track: Boolean,
    var url: String
)