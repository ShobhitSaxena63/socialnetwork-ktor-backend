package com.shobhit63.routes

import com.shobhit63.controller.user.UserController
import com.shobhit63.data.models.User
import com.shobhit63.data.requests.CreateAccountRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.util.ApiResponseMessages.FIELDS_BLANK
import com.shobhit63.util.ApiResponseMessages.USER_ALREADY_EXISTS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userController: UserController by inject<UserController>()
    route("/api/user/create") {
        post {
            val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExist = userController.getUserByEmail(request.email) != null
            if (userExist) {
                call.respond(
                    BasicApiResponse(
                        successful = false,
                        message = USER_ALREADY_EXISTS
                    )
                )
                return@post
            }

            if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
                call.respond(
                    BasicApiResponse(
                        successful = false,
                        message = FIELDS_BLANK
                    )
                )
                return@post
            }
            userController.createUser(
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
            call.respond(
                BasicApiResponse(
                    successful = true,
                )
            )
        }
    }
}