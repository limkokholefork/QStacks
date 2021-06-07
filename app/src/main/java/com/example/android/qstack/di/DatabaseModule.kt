package com.example.android.qstack.di

import android.content.Context
import androidx.room.Room
import com.example.android.qstack.db.QStacksDB
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
    fun provideDatabase(@ApplicationContext context: Context) : QStacksDB{
        return Room.databaseBuilder(context,
            QStacksDB::class.java,
            "QStack_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLogDao(qStacksDB: QStacksDB) = qStacksDB.getQuestionDao()
}