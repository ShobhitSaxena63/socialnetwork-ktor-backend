package com.shobhit63.service

import com.shobhit63.data.models.User
import com.shobhit63.data.requests.CreateAccountRequest
import com.shobhit63.data.requests.UpdateProfileRequest
import com.shobhit63.data.response.ProfileResponse
import com.shobhit63.data.response.UserResponseItem
import com.shobhit63.repository.follow.FollowRepository
import com.shobhit63.repository.user.UserRepository

class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {
    suspend fun doesUserWithEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }

    suspend fun doesEmailBelongsToUserId(email: String, userId: String): Boolean {
        return userRepository.doesEmailBelongToUserId(email, userId)
    }

    suspend fun getUserProfile(userId: String, callerUserId: String): ProfileResponse? {
        val user = userRepository.getUserById(userId) ?: return null
        return ProfileResponse(
            username = user.username,
            bio = user.bio,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            postCount = user.postCount,
            profilePictureUrl = user.profileImageUrl,
            topSkillUrls = user.skills,
            gitHubUrl = user.gitHubUrl,
            instagramUrl = user.instagramURl,
            linkedInUrl = user.linkedInURl,
            isOwnProfile = userId == callerUserId,
            isFollowing = if (userId != callerUserId) followRepository.doesUserFollow(callerUserId, userId) else false
        )
    }

    suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    fun isValidPassword(enteredPassword: String, actualPassword: String): Boolean {
        return enteredPassword == actualPassword
    }

    suspend fun updateUser(
        userId: String,
        profileImageUrl: String,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        return userRepository.updateUser(userId,profileImageUrl, updateProfileRequest)
    }

    suspend fun searchForUsers(query: String, userId: String): List<UserResponseItem> {
        val users = userRepository.searchForUsers(query)
        val followsByUser = followRepository.getFollowsByUser(userId)
        return users.map { user ->
            val isFollowing = followsByUser.find { it.followedUserId == user.id } != null
            UserResponseItem(
                username = user.username,
                profilePictureUrl = user.profileImageUrl,
                bio = user.bio,
                isFollowing = isFollowing
            )
        }
    }

    suspend fun createUser(request: CreateAccountRequest) {
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                bio = "",
                gitHubUrl = null,
                instagramURl = null,
                linkedInURl = null,

                )
        )
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest): ValidationEvent {
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }

    sealed class ValidationEvent {
        object ErrorFieldEmpty : ValidationEvent()
        object Success : ValidationEvent()
    }
}