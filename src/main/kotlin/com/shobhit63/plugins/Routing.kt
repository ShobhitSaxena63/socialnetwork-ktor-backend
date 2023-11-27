package com.shobhit63.plugins

import com.shobhit63.repository.follow.FollowRepository
import com.shobhit63.repository.post.PostRepository
import com.shobhit63.repository.user.UserRepository
import com.shobhit63.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject<UserRepository>()
    val followRepository: FollowRepository by inject<FollowRepository>()
    val postRepository: PostRepository by inject<PostRepository>()

    routing {
        //User routes
        createUserRoute(userRepository)
        loginUser(userRepository)

        //Following routes
        followUser(followRepository)
        unfollowUser(followRepository)

        //Post routes
        createPostRoute(postRepository)
    }
}
