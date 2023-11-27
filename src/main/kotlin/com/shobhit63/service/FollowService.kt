package com.shobhit63.service

import com.shobhit63.data.requests.FollowUpdateRequest
import com.shobhit63.repository.follow.FollowRepository

class FollowService(
    private val followRepository: FollowRepository
) {
    suspend fun followUserIfExists(request: FollowUpdateRequest) :Boolean{
        return followRepository.followUserIfExists(
            request.followingUserId,
            request.followedUserId
        )
    }

    suspend fun unfollowUserIfExists(request: FollowUpdateRequest) :Boolean{
        return followRepository.unfollowUserIfExists(
            request.followingUserId,
            request.followedUserId
        )
    }

}