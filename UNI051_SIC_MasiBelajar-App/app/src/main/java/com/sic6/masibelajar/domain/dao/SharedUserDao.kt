package com.sic6.masibelajar.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sic6.masibelajar.domain.entities.Point
import com.sic6.masibelajar.domain.entities.SharedUser
import kotlinx.coroutines.flow.Flow

@Dao
interface SharedUserDao {

    @Query("SELECT * FROM shared_user")
    suspend fun getSharedUsers(): List<SharedUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSharedUser(user: SharedUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSharedUsers(users: List<SharedUser>)

    @Query("DELETE FROM shared_user")
    suspend fun deleteSharedUser()

    @Transaction
    suspend fun resetSharedUser(users: List<SharedUser>) {
        deleteSharedUser()
        insertSharedUsers(users)
    }
}
