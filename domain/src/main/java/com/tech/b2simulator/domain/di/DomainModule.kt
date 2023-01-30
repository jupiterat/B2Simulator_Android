package com.tech.b2simulator.domain.di

import com.tech.b2simulator.domain.repository.CategoryRepository
import com.tech.b2simulator.domain.repository.QuestionRepository
import com.tech.b2simulator.domain.usecase.GetActionCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCategoryUseCase(
        repository: CategoryRepository,
        questionRepository: QuestionRepository
    ): GetActionCategoryUseCase {
        return GetActionCategoryUseCase(
            repository,
            questionRepository
        )
    }
}