package com.shobhit63.controller.user

import com.shobhit63.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserControllerImpl(
   db: CoroutineDatabase
) : UserController {

    private val users = db.getCollection<User>()
    override suspend fun createUser(user: User) {
        users.insertOne(user)
    }

    override suspend fun getUserById(id: String): User? {
        return users.findOneById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }
}