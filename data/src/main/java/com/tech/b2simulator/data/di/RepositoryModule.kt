package com.tech.b2simulator.data.di

import com.tech.b2simulator.data.repository.CategoryImpl
import com.tech.b2simulator.data.repository.ExamRepositoryImpl
import com.tech.b2simulator.data.repository.QuestionsRepositoryImpl
import com.tech.b2simulator.domain.repository.CategoryRepository
import com.tech.b2simulator.domain.repository.ExamRepository
import com.tech.b2simulator.domain.repository.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(impl: CategoryImpl): CategoryRepository

    @Binds
    abstract fun bindQuestionRepository(impl: QuestionsRepositoryImpl): QuestionRepository

    @Binds
    abstract fun bindExamRepository(impl: ExamRepositoryImpl): ExamRepository
}