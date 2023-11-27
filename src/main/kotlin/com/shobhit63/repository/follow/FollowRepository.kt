package com.shobhit63.repository.follow

interface FollowRepository {
    suspend fun followUserIfExists(
        followingUserId: String,
        followedUserId: String
    ):Boolean

    suspend fun unfollowUserIfExists(
        followingUserId: String,
        followedUserId: String
    ):Boolean
}