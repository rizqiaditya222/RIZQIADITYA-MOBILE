package com.kotlin.rizqiaditya.di

import com.kotlin.rizqiaditya.data.repository.CommentRepositoryImpl
import com.kotlin.rizqiaditya.domain.repository.CommentRepository
import com.kotlin.rizqiaditya.data.repository.MessageRepositoryImpl
import com.kotlin.rizqiaditya.domain.repository.MessageRepository
import com.kotlin.rizqiaditya.data.repository.ProjectRepositoryImpl
import com.kotlin.rizqiaditya.domain.repository.ProjectRepository
import com.kotlin.rizqiaditya.data.repository.StatiticRepositoryImpl
import com.kotlin.rizqiaditya.domain.repository.StatiticRepository
import com.kotlin.rizqiaditya.data.repository.StoryRepositoryImpl
import com.kotlin.rizqiaditya.domain.repository.StoryRepository
import com.kotlin.rizqiaditya.domain.usecase.comment.SendCommentUseCase
import com.kotlin.rizqiaditya.domain.usecase.message.GetMessagesUseCase
import com.kotlin.rizqiaditya.domain.usecase.message.SendMessageUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.CreateProjectUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.DeleteProjectUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.EditProjectUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.GetProjectsUseCase
import com.kotlin.rizqiaditya.domain.usecase.statistic.GetTodayStatisticUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.DeleteStoryUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.GetStoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCommentRepository(
        impl: CommentRepositoryImpl
    ): CommentRepository

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        impl: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    @Singleton
    abstract fun bindProjectRepository(
        impl: ProjectRepositoryImpl
    ): ProjectRepository

    @Binds
    @Singleton
    abstract fun bindStatisticRepository(
        impl: StatiticRepositoryImpl
    ): StatiticRepository

    @Binds
    @Singleton
    abstract fun bindStoryRepository(
        impl: StoryRepositoryImpl
    ): StoryRepository

    companion object {
        @Provides
        @Singleton
        fun provideSendCommentUseCase(repository: CommentRepository): SendCommentUseCase {
            return SendCommentUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideGetMessagesUseCase(repository: MessageRepository): GetMessagesUseCase {
            return GetMessagesUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideSendMessageUseCase(repository: MessageRepository): SendMessageUseCase {
            return SendMessageUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideCreateProjectUseCase(repository: ProjectRepository): CreateProjectUseCase {
            return CreateProjectUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideGetProjectsUseCase(repository: ProjectRepository): GetProjectsUseCase {
            return GetProjectsUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideEditProjectUseCase(repository: ProjectRepository): EditProjectUseCase {
            return EditProjectUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideDeleteProjectUseCase(repository: ProjectRepository): DeleteProjectUseCase {
            return DeleteProjectUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideGetTodayStatisticUseCase(repository: StatiticRepository): GetTodayStatisticUseCase {
            return GetTodayStatisticUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideGetStoriesUseCase(repository: StoryRepository): GetStoriesUseCase {
            return GetStoriesUseCase(repository)
        }

        @Provides
        @Singleton
        fun provideDeleteStoryUseCase(repository: StoryRepository): DeleteStoryUseCase {
            return DeleteStoryUseCase(repository)
        }
    }
}