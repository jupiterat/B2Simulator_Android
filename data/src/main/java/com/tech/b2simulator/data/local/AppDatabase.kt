package com.tech.b2simulator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tech.b2simulator.data.local.entity.CategoryAction
import com.tech.b2simulator.data.local.entity.CategoryLocation
import com.tech.b2simulator.data.local.entity.Questions

@Database(
    entities = [CategoryAction::class, CategoryLocation::class, Questions::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val questionDao: QuestionsDao
}