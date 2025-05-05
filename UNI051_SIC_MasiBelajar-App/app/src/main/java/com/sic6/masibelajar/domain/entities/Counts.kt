package com.sic6.masibelajar.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Counts(
    val inside: Int,
    @SerialName("non-toddler") val non_toddler: Int,
    val toddler: Int
)