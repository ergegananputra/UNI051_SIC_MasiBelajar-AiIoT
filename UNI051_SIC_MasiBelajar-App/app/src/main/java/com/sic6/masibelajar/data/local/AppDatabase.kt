package com.sic6.masibelajar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sic6.masibelajar.domain.dao.HistoryDao
import com.sic6.masibelajar.domain.dao.SharedUserDao
import com.sic6.masibelajar.domain.entities.History
import com.sic6.masibelajar.domain.entities.SharedUser

@Database(
    version = 1,
    entities = [SharedUser::class, History::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sharedUserDao() : SharedUserDao
    abstract fun historyDao() : HistoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lokari_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}