package com.sic6.masibelajar.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "shared_user")
    data class SharedUser(
        @PrimaryKey
        val id: Int,
        val email: String,
        val url: String,
    )