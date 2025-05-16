package com.sic6.masibelajar.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sic6.masibelajar.domain.entities.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY time DESC")
    suspend fun getAllHistory(): List<History>

    @Insert
    suspend fun insertHistory(history: History)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistory()

    @Query("DELETE FROM history WHERE strftime('%Y-%m-%d', time) < strftime('%Y-%m-%d', 'now', 'localtime')")
    suspend fun deleteOldHistory()
}