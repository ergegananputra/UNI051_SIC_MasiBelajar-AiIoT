package com.sic6.masibelajar.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val counts: Counts,
    val fall: Boolean,
    val frame_height: Int? = 1080,
    val frame_width: Int? = 1920,
    val id: String? = null,
    val is_there_something_wrong: Boolean? = null,
    val out_of_safezone: Boolean,
    val timestamp: String,
)