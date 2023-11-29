package com.shobhit63.repository.comment

import com.shobhit63.data.models.Comment
import com.shobhit63.data.models.Post

interface CommentRepository {
    suspend fun createComment(comment: Comment):String

    suspend fun deleteComment(commentId:String) :Boolean

    suspend fun deleteCommentsFromPost(postId:String):Boolean

    suspend fun getCommentsForPost(postId:String):List<Comment>

    suspend fun getComment(commentId:String):Comment?
}