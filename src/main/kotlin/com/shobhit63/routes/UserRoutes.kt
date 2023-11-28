package com.shobhit63.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.shobhit63.data.requests.CreateAccountRequest
import com.shobhit63.data.requests.LoginRequest
import com.shobhit63.data.response.AuthResponse
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.service.UserService
import com.shobhit63.util.ApiResponseMessages.FIELDS_BLANK
import com.shobhit63.util.ApiResponseMessages.INVALID_CREDENTIALS
import com.shobhit63.util.ApiResponseMessages.USER_ALREADY_EXISTS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.createUser(userService: UserService) {
    post("/api/user/create") {
        val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        if (userService.doesUserWithEmailExist(request.email)) {
            call.respond(
                BasicApiResponse(
                    successful = false,
                    message = USER_ALREADY_EXISTS
                )
            )
            return@post
        }

        when (userService.validateCreateAccountRequest(request)) {
            is UserService.ValidationEvent.ErrorFieldEmpty -> {
                call.respond(
                    BasicApiResponse(
                        successful = false,
                        message = FIELDS_BLANK

                    )
                )
            }

            is UserService.ValidationEvent.Success -> {
                userService.createUser(request)
                call.respond(
                    BasicApiResponse(
                        successful = true,
                    )
                )
            }
        }

    }
}

fun Route.loginUser(
    userService: UserService,
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {
    post("/api/user/login") {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (request.email.isBlank() || request.password.isBlank()) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val isCorrectPassword = userService.doesPasswordMatchForUser(request)
        if (isCorrectPassword) {
            val expiresIn = 1000L * 60L * 60L * 24L * 365L
            val token = JWT
                .create()
                .withClaim("email", request.email)
                .withIssuer(jwtIssuer)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(
                    token = token
                )
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = false,
                    message = INVALID_CREDENTIALS
                )
            )
        }

    }
}