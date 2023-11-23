package com.shobhit63.controller.user

import com.shobhit63.data.models.User

interface UserController {

    suspend fun createUser(user: User)
    suspend fun getUserById(id:String):User?
    suspend fun getUserByEmail(email:String):User?

}