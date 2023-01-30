package com.tech.b2simulator.data.di

import android.app.Application
import androidx.room.Room
import com.tech.b2simulator.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
//    @Provides
//    @Singleton
//    fun provideCategoryRepository(db: AppDatabase): CategoryRepository {
//        return CategoryImpl(db.categoryDao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideQuestionRepository(db: AppDatabase): QuestionRepository {
//        return QuestionsRepositoryImpl(db.questionDao)
//    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "120mophong.db"
        )
            .createFromAsset("db/120mophong.db").build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao

    @Singleton
    @Provides
    fun provideQuestionDao(db: AppDatabase) = db.questionDao
}