package com.sic6.masibelajar.di

import android.content.Context
import com.sic6.masibelajar.data.local.AppDatabase
import com.sic6.masibelajar.domain.dao.HistoryDao
import com.sic6.masibelajar.domain.dao.SharedUserDao
import com.sic6.masibelajar.domain.entities.SharedUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideSharedUserDao(database: AppDatabase): SharedUserDao {
        return database.sharedUserDao()
    }

    @Provides
    fun provideHistory(database: AppDatabase): HistoryDao {
        return database.historyDao()
    }
}