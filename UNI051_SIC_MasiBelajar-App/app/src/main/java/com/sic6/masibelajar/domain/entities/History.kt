package com.sic6.masibelajar.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sic6.masibelajar.domain.enums.EventType

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: EventType,
    val name: String,
    val time: String,
)
