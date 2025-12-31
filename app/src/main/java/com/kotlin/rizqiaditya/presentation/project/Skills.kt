package com.kotlin.rizqiaditya.presentation.project

enum class Skill {
    HTML,
    CSS,
    TAILWIND,
    JAVASCRIPT,
    TYPESCRIPT,
    NEXTJS,
    REACTJS,
    KOTLIN,
    JETPACKCOMPOSE,
    DART,
    FLUTTER,
    GITHUB,
    DOCKER
}

fun Skill.label(): String = when (this) {
    Skill.HTML -> "HTML"
    Skill.CSS -> "CSS"
    Skill.TAILWIND -> "Tailwind"
    Skill.JAVASCRIPT -> "JavaScript"
    Skill.TYPESCRIPT -> "TypeScript"
    Skill.NEXTJS -> "Next.js"
    Skill.REACTJS -> "React.js"
    Skill.KOTLIN -> "Kotlin"
    Skill.JETPACKCOMPOSE -> "Jetpack Compose"
    Skill.DART -> "Dart"
    Skill.FLUTTER -> "Flutter"
    Skill.GITHUB -> "GitHub"
    Skill.DOCKER -> "Docker"
}
