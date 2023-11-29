package com.shobhit63.service

import com.shobhit63.data.util.ParentType
import com.shobhit63.repository.likes.LikeRepository

class LikeService(
    private val repository:LikeRepository
) {
    suspend fun likeParent(userId:String,parentId:String,parentType: Int):Boolean {
        return repository.likeParent(userId,parentId, parentType)
    }
    suspend fun unLikeParent(userId:String,parentId:String):Boolean {
        return repository.unlikeParent(userId,parentId)
    }

    suspend fun deleteLikesForParent(parentId: String) {
        repository.deleteLikesForParent(parentId)
    }
}