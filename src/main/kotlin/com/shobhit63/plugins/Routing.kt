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
    val userRepository: UserRepository by inject<UserRepository>()
    val userService: UserService by inject<UserService>()
    val followService: FollowService by inject<FollowService>()
    val postService: PostService by inject<PostService>()


    routing {
        //User routes
        createUserRoute(userService)
        loginUser(userRepository)

        //Following routes
        followUser(followService)
        unfollowUser(followService)

        //Post routes
        createPostRoute(postService)
    }
}
