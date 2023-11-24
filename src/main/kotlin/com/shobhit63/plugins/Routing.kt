package com.shobhit63.plugins

import com.shobhit63.repository.user.UserRepository
import com.shobhit63.routes.createUserRoute
import com.shobhit63.routes.loginUser
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject<UserRepository>()

    routing {
        createUserRoute(userRepository)
        loginUser(userRepository)
    }
}
