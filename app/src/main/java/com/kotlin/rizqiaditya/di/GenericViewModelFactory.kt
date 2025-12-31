@file:Suppress("unused")

package com.kotlin.rizqiaditya.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("unused")
class GenericViewModelFactory(
    private val container: AppContainer = AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            when (modelClass.name) {
                "com.kotlin.rizqiaditya.presentation.message.MessageViewModel" -> {
                    val cls = Class.forName("com.kotlin.rizqiaditya.presentation.message.MessageViewModel")
                    val ctor = cls.getConstructor(Class.forName("com.kotlin.rizqiaditya.domain.usecase.message.GetMessagesUseCase"))
                    ctor.newInstance(container.getMessagesUseCase) as T
                }

                "com.kotlin.rizqiaditya.presentation.project.ProjectViewModel" -> {
                    val cls = Class.forName("com.kotlin.rizqiaditya.presentation.project.ProjectViewModel")
                    val ctor = cls.getConstructor(Class.forName("com.kotlin.rizqiaditya.domain.usecase.project.GetProjectsUseCase"))
                    ctor.newInstance(container.getProjectsUseCase) as T
                }

                "com.kotlin.rizqiaditya.presentation.home.HomeViewModel" -> {
                    val cls = Class.forName("com.kotlin.rizqiaditya.presentation.home.HomeViewModel")
                    val ctor = cls.getConstructor(
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.story.GetStoriesUseCase"),
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.statistic.GetTodayStatisticUseCase")
                    )
                    ctor.newInstance(container.getStoriesUseCase, container.getTodayStatisticUseCase) as T
                }

                "com.kotlin.rizqiaditya.presentation.story.StoryViewModel" -> {
                    val cls = Class.forName("com.kotlin.rizqiaditya.presentation.story.StoryViewModel")
                    val ctor = cls.getConstructor(
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.story.CreateStoryUseCase"),
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.comment.GetAllCommentsUseCase"),
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.comment.GetCommentsByStoryIdUseCase"),
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.story.GetStoriesUseCase")
                    )
                    ctor.newInstance(container.createStoryUseCase, container.getAllCommentsUseCase, container.getCommentsByStoryIdUseCase, container.getStoriesUseCase) as T
                }

                "com.kotlin.rizqiaditya.presentation.project.ProjectFormViewModel" -> {
                    val cls = Class.forName("com.kotlin.rizqiaditya.presentation.project.ProjectFormViewModel")
                    val ctor = cls.getConstructor(
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.project.CreateProjectUseCase"),
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.project.EditProjectUseCase"),
                        Class.forName("com.kotlin.rizqiaditya.domain.usecase.project.GetProjectsUseCase")
                    )
                    ctor.newInstance(container.createProjectUseCase, container.editProjectUseCase, container.getProjectsUseCase) as T
                }

                "com.kotlin.rizqiaditya.presentation.navigation.MainViewModel" -> {
                    val cls = Class.forName("com.kotlin.rizqiaditya.presentation.navigation.MainViewModel")
                    val ctor = cls.getConstructor()
                    ctor.newInstance() as T
                }

                else -> {
                    // Fallback to NewInstanceFactory which will attempt no-arg constructor
                    val factory = ViewModelProvider.NewInstanceFactory()
                    factory.create(modelClass)
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to create ViewModel for ${modelClass.name}", e)
        }
    }
}
