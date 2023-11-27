package com.shobhit63.repository.post

import com.shobhit63.data.models.Post
import com.shobhit63.util.Constants.DEFAULT_POST_PAGE_SIZE

interface PostRepository {
    suspend fun createPostIfUserExists(post: Post):Boolean
    suspend fun deletePost(postId:String)
    suspend fun getPostsByFollows(
        userId: String,
        page: Int = 0,
        pageSize: Int = DEFAULT_POST_PAGE_SIZE
    ):List<Post>
}