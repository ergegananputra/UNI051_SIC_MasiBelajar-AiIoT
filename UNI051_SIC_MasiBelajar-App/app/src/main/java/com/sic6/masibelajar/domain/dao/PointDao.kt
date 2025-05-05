package com.sic6.masibelajar.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sic6.masibelajar.domain.entities.Point

@Dao
interface PointDao {

    @Query("SELECT * FROM points")
    suspend fun getPoints(): List<Point>

    @Insert
    suspend fun insertPoints(points: List<Point>)

    @Query("DELETE FROM points")
    suspend fun deletePoints()

    @Transaction
    suspend fun resetPoints(points: List<Point>) {
        deletePoints()
        insertPoints(points)
    }
}