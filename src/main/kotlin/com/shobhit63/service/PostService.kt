package com.shobhit63.service

import com.shobhit63.data.models.Post
import com.shobhit63.data.requests.CreatePostRequest
import com.shobhit63.repository.post.PostRepository
import com.shobhit63.util.Constants
import com.shobhit63.util.Constants.DEFAULT_POST_PAGE_SIZE

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

    suspend fun getPostsForFollows(
        userId:String,
        page:Int = 0,
        pageSize :Int = DEFAULT_POST_PAGE_SIZE
    ): List<Post> {
        return repository.getPostsByFollows(
            userId, page, pageSize
        )
    }
    suspend fun getPost(postId:String):Post? = repository.getPost(postId)

    suspend fun deletePost(postId:String) {
        repository.deletePost(postId)
    }


}