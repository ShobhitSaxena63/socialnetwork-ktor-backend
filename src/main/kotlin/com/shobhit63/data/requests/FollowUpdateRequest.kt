package com.shobhit63.data.requests

data class FollowUpdateRequest(
    val followingUserId:String,
    val followedUserId:String,
)
