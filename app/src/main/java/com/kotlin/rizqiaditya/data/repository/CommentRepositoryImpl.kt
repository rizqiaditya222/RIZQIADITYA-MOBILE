package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.remote.api.CommentApi
import com.kotlin.rizqiaditya.domain.model.Comment
import com.kotlin.rizqiaditya.domain.repository.CommentRepository
import javax.inject.Inject
import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.dto.comment.CommentRequestDto


class CommentRepositoryImpl @Inject constructor(
    private val commentApi: CommentApi
) : CommentRepository {
    override suspend fun getAllComments(): List<Comment> {
        val response = commentApi.getAllComments()
        return response.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getCommentsByStoryId(storyId: String): List<Comment> {
        val response = commentApi.getCommentByStoryId(storyId)
        return response.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun sendComment(storyId: String, comment: String) {
        val response = commentApi.sendComment(
            CommentRequestDto(
                storyId = storyId,
                comment = comment
            )
        )
    }

    override suspend fun deleteComment(commentId: String) {
        commentApi.deleteComment(commentId)
    }
}