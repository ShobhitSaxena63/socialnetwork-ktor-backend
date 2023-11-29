package com.shobhit63.service

import com.shobhit63.data.requests.FollowUpdateRequest
import com.shobhit63.repository.follow.FollowRepository

class FollowService(
    private val followRepository: FollowRepository
) {
    suspend fun followUserIfExists(request: FollowUpdateRequest,followingUserId:String) :Boolean{
        return followRepository.followUserIfExists(
            followingUserId,
            request.followedUserId
        )
    }

    suspend fun unfollowUserIfExists(request: FollowUpdateRequest,followingUserId:String) :Boolean{
        return followRepository.unfollowUserIfExists(
            followingUserId,
            request.followedUserId
        )
    }

}