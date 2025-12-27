package com.kotlin.rizqiaditya.domain.usecase.comment

import com.kotlin.rizqiaditya.domain.repository.CommentRepository
import javax.inject.Inject

class SendCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(
        storyId: String,
        comment: String
    ) {
        repository.sendComment(
        storyId,
        comment
        )
    }
}