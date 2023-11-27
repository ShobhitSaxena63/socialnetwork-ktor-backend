package com.shobhit63.plugins

import com.shobhit63.repository.follow.FollowRepository
import com.shobhit63.repository.post.PostRepository
import com.shobhit63.repository.user.UserRepository
import com.shobhit63.routes.*
import com.shobhit63.service.FollowService
import com.shobhit63.service.PostService
import com.shobhit63.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: UserService by inject<UserService>()
    val followService: FollowService by inject<FollowService>()
    val postService: PostService by inject<PostService>()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        //User routes
        createUserRoute(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
            )

        //Following routes
        followUser(followService)
        unfollowUser(followService)

        //Post routes
        createPostRoute(postService,userService)
        getPostsForFollows(postService, userService)
    }
}
