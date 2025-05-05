package com.sic6.masibelajar.domain.entities

import com.sic6.masibelajar.domain.enums.EventType

data class History(
    val type: EventType,
    val name: String,
    val time: String,
)
