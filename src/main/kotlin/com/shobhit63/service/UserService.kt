package com.shobhit63.service

import com.shobhit63.data.models.User
import com.shobhit63.data.requests.CreateAccountRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.repository.user.UserRepository
import com.shobhit63.util.ApiResponseMessages
import io.ktor.server.application.*
import io.ktor.server.response.*

class UserService(
    private val repository:UserRepository
) {
    suspend fun doesUserWithEmailExist(email:String):Boolean {
        return repository.getUserByEmail(email) != null
    }

    suspend fun createUser(request: CreateAccountRequest){
        repository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                bio = "",
                gitHubUrl = null,
                instagramURl = null,
                linkedInURl = null
            )
        )
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest):ValidationEvent{
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }

    sealed class ValidationEvent{
        object ErrorFieldEmpty:ValidationEvent()
        object Success:ValidationEvent()
    }
}