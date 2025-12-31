package com.kotlin.rizqiaditya.presentation.story

import java.io.File

sealed class StoryEvent {
    data class DescriptionChanged(val value: String) : StoryEvent()
    data class ImageSelected(val file: File?) : StoryEvent()
    object Submit : StoryEvent()
    data class LoadForEdit(val id: String) : StoryEvent()
}

