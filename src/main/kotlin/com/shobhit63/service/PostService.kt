package com.shobhit63.service

import com.shobhit63.data.models.Post
import com.shobhit63.data.requests.CreatePostRequest
import com.shobhit63.repository.post.PostRepository

class PostService(
    private val repository: PostRepository
) {
    suspend fun createPostIfUserExists(request:CreatePostRequest):Boolean {
        return repository.createPostIfUserExists(
            Post(
                imageUrl = "",
                userId = request.userId,
                timeStamp = System.currentTimeMillis(),
                description = request.description
            )
        )
    }
}