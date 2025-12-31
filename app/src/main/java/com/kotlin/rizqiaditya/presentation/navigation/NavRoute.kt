package com.kotlin.rizqiaditya.presentation.navigation

object NavRoute {
    const val SPLASH = "splash"
    const val MAIN = "main"
    const val HOME = "home"
    const val STORY = "story"
    // simple form routes (no params)
    const val STORYFORM = "story_form"
    const val PROJECTFORM = "project_form"

    // pattern routes for form screens (use query params isEdit and id)
    const val STORYFORM_PATTERN = "story_form?isEdit={isEdit}&id={id}"
    const val PROJECTFORM_PATTERN = "project_form?isEdit={isEdit}&id={id}"
    const val PROJECT = "project"
    const val MESSAGE = "message"

    // helpers to build navigation routes
    fun storyFormRoute(isEdit: Boolean = false, id: String = ""): String {
        return "story_form?isEdit=$isEdit&id=$id"
    }

    fun projectFormRoute(isEdit: Boolean = false, id: String = ""): String {
        return "project_form?isEdit=$isEdit&id=$id"
    }
}
