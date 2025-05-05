package com.sic6.masibelajar.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class Point(
    @PrimaryKey
    val id: Int,
    val x: Int,
    val y: Int,
)
