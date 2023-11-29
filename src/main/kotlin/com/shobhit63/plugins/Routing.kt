package com.shobhit63.plugins

import com.shobhit63.routes.*
import com.shobhit63.service.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: UserService by inject<UserService>()
    val followService: FollowService by inject<FollowService>()
    val postService: PostService by inject<PostService>()
    val likeService: LikeService by inject<LikeService>()
    val commentService: CommentService by inject<CommentService>()
    val activityService: ActivityService by inject<ActivityService>()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        //User routes
        createUser(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
            )
        searchUser(userService)

        //Following routes
        followUser(followService,activityService)
        unfollowUser(followService)

        //Post routes
        createPost(postService)
        getPostsForFollows(postService)
        deletePost(postService,likeService,commentService)

        //Like routes
        likeParent(likeService,activityService)
        unLikeParent(likeService)

        //Comment routes
        createComment(commentService,activityService)
        deleteComment(commentService, likeService)
        getCommentsForPost(commentService)

        //Activity routes
        getActivities(activityService)


    }
}
