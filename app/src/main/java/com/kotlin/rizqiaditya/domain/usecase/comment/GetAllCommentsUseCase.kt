package com.kotlin.rizqiaditya.domain.usecase.comment

import com.kotlin.rizqiaditya.domain.model.Comment
import com.kotlin.rizqiaditya.domain.repository.CommentRepository
import javax.inject.Inject

class GetAllCommentsUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke() {
        repository.getAllComments()
    }
}