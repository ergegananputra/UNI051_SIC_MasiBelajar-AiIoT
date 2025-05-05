package com.sic6.masibelajar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sic6.masibelajar.domain.dao.PointDao
import com.sic6.masibelajar.domain.entities.Point

@Database(
    version = 1,
    entities = [Point::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pointDao(): PointDao

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