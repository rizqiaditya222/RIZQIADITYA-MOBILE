package com.kotlin.rizqiaditya.domain.usecase.comment

import com.kotlin.rizqiaditya.domain.model.Comment
import com.kotlin.rizqiaditya.domain.repository.CommentRepository

class GetAllCommentsUseCase constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(): List<Comment> = repository.getAllComments()
}