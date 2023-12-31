package com.shobhit63.repository.user

import com.shobhit63.data.models.User
import com.shobhit63.data.requests.UpdateProfileRequest

interface UserRepository {

    suspend fun createUser(user: User)
    suspend fun getUserById(id:String):User?
    suspend fun getUserByEmail(email:String):User?

    suspend fun updateUser(userId: String,profileImageUrl:String,updateProfileRequest: UpdateProfileRequest):Boolean
    suspend fun doesPasswordForUserMatch(email:String, enteredPassword:String):Boolean

    suspend fun doesEmailBelongToUserId(email: String,userId:String) :Boolean

    suspend fun searchForUsers(query:String):List<User>

}