package com.sic6.masibelajar.di

import android.content.Context
import com.sic6.masibelajar.data.local.AppDatabase
import com.sic6.masibelajar.domain.dao.PointDao
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
    fun providePointDao(database: AppDatabase): PointDao {
        return database.pointDao()
    }
}