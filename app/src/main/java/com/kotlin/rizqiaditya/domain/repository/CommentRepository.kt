package com.kotlin.rizqiaditya.domain.repository

import com.kotlin.rizqiaditya.domain.model.Comment

interface CommentRepository {

    suspend fun getAllComments(): List<Comment>

    suspend fun getCommentsByStoryId(storyId: String): List<Comment>

    suspend fun sendComment(storyId: String, comment: String)

    suspend fun deleteComment(commentId: String)
}