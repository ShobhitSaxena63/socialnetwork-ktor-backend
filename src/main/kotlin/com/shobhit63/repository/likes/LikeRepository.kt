package com.shobhit63.repository.likes

interface LikeRepository {
    suspend fun likeParent(userId:String, parentId:String):Boolean
    suspend fun unlikeParent(userId: String, parentId: String):Boolean

    suspend fun deleteLikesForParent(parentId:String)


}