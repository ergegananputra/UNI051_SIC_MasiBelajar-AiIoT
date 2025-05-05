package com.sic6.masibelajar.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val frame: String,
    val results: Results
)