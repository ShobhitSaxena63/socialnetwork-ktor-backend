package com.shobhit63.data.response

import com.shobhit63.data.models.Following

data class ProfileResponse(
    val username:String,
    val bio:String,
    val followerCount:Int,
    val followingCount:Int,
    val postCount:Int,
    val profilePictureUrl:String,
    val topSkillUrls:List<String>,
    val gitHubUrl:String?,
    val instagramUrl:String?,
    val linkedInUrl:String?,
    val isOwnProfile:Boolean,
    val isFollowing: Boolean
)