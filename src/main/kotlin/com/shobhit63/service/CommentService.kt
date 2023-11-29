package com.shobhit63.service

import com.shobhit63.data.models.Comment
import com.shobhit63.data.requests.CreateCommentRequest
import com.shobhit63.repository.comment.CommentRepository
import com.shobhit63.util.Constants

class CommentService(
    private val repository:CommentRepository
) {
    suspend fun createComment(createCommentRequest: CreateCommentRequest, userId:String):ValidationEvents {
        createCommentRequest.apply {
            if(comment.isBlank() || postId.isBlank()) {
                return ValidationEvents.ErrorFieldEmpty
            }
            if(comment.length > Constants.MAX_COMMENT_LENGTH) {
                return ValidationEvents.ErrorCommentTooLong
            }
        }
       repository.createComment(
            comment = Comment(
                comment =  createCommentRequest.comment,
                userId = userId,
                postId = createCommentRequest.postId,
                timeStamp = System.currentTimeMillis()
            )
        )
        return ValidationEvents.Success
    }

    suspend fun deleteCommentsFromPost(postId: String) {
         repository.deleteCommentsFromPost(postId)
    }
    suspend fun deleteComment(commentId:String):Boolean {
        return repository.deleteComment(commentId)
    }

    suspend fun getCommentsForPost(postId:String):List<Comment> {
        return repository.getCommentsForPost(postId)
    }

    suspend fun getCommentById(commentId: String):Comment? {
        return repository.getComment(commentId)
    }

    sealed class ValidationEvents {
        object ErrorFieldEmpty:ValidationEvents()
        object ErrorCommentTooLong:ValidationEvents()
        object Success:ValidationEvents()
    }


}