package com.kotlin.rizqiaditya.di

import com.kotlin.rizqiaditya.data.repository.CommentRepositoryImpl
import com.kotlin.rizqiaditya.data.repository.MessageRepositoryImpl
import com.kotlin.rizqiaditya.data.repository.ProjectRepositoryImpl
import com.kotlin.rizqiaditya.data.repository.StatiticRepositoryImpl
import com.kotlin.rizqiaditya.data.repository.StoryRepositoryImpl
import com.kotlin.rizqiaditya.domain.usecase.comment.GetAllCommentsUseCase
import com.kotlin.rizqiaditya.domain.usecase.comment.GetCommentsByStoryIdUseCase
import com.kotlin.rizqiaditya.domain.usecase.comment.SendCommentUseCase
import com.kotlin.rizqiaditya.domain.usecase.message.GetMessagesUseCase
import com.kotlin.rizqiaditya.domain.usecase.message.SendMessageUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.CreateProjectUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.DeleteProjectUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.EditProjectUseCase
import com.kotlin.rizqiaditya.domain.usecase.project.GetProjectsUseCase
import com.kotlin.rizqiaditya.domain.usecase.statistic.GetTodayStatisticUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.CreateStoryUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.DeleteStoryUseCase
import com.kotlin.rizqiaditya.domain.usecase.story.GetStoriesUseCase

/**
 * Simple manual service locator / DI container to create singletons used across the app.
 */
object AppContainer {
    // network
    private val okHttp by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideRetrofit(okHttp) }

    // apis
    private val storyApi by lazy { NetworkModule.provideStoryApi(retrofit) }
    private val projectApi by lazy { NetworkModule.provideProjectApi(retrofit) }
    private val messageApi by lazy { NetworkModule.provideMessageApi(retrofit) }
    private val commentApi by lazy { NetworkModule.provideCommentApi(retrofit) }
    private val statisticApi by lazy { NetworkModule.provideStatisticApi(retrofit) }

    // repositories
    private val storyRepository by lazy { StoryRepositoryImpl(storyApi) }
    private val projectRepository by lazy { ProjectRepositoryImpl(projectApi) }
    private val messageRepository by lazy { MessageRepositoryImpl(messageApi) }
    private val commentRepository by lazy { CommentRepositoryImpl(commentApi) }
    private val statiticRepository by lazy { StatiticRepositoryImpl(statisticApi) }

    // use-cases
    val getStoriesUseCase by lazy { GetStoriesUseCase(storyRepository) }
    val createStoryUseCase by lazy { CreateStoryUseCase(storyRepository) }
    val deleteStoryUseCase by lazy { DeleteStoryUseCase(storyRepository) }

    val getProjectsUseCase by lazy { GetProjectsUseCase(projectRepository) }
    val createProjectUseCase by lazy { CreateProjectUseCase(projectRepository) }
    val editProjectUseCase by lazy { EditProjectUseCase(projectRepository) }
    val deleteProjectUseCase by lazy { DeleteProjectUseCase(projectRepository) }

    val getMessagesUseCase by lazy { GetMessagesUseCase(messageRepository) }
    val sendMessageUseCase by lazy { SendMessageUseCase(messageRepository) }

    val getAllCommentsUseCase by lazy { GetAllCommentsUseCase(commentRepository) }
    val getCommentsByStoryIdUseCase by lazy { GetCommentsByStoryIdUseCase(commentRepository) }
    val sendCommentUseCase by lazy { SendCommentUseCase(commentRepository) }

    val getTodayStatisticUseCase by lazy { GetTodayStatisticUseCase(statiticRepository) }
}
