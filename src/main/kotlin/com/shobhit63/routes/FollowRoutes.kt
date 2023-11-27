package com.shobhit63.routes

import com.shobhit63.data.requests.FollowUpdateRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.repository.follow.FollowRepository
import com.shobhit63.util.ApiResponseMessages.USER_NOT_FOUND
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.followUser(followRepository: FollowRepository) {
    post("/api/following/follow") {
        val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val didUserExist = followRepository.followUserIfExists(
            request.followingUserId, request.followedUserId
        )
        if (didUserExist) {
            call.respond(
                HttpStatusCode.OK, BasicApiResponse(
                    successful = true,

                    )
            )
        } else {
            call.respond(
                HttpStatusCode.OK, BasicApiResponse(
                    successful = false, message = USER_NOT_FOUND
                )
            )
        }
    }
}


fun Route.unfollowUser(followRepository: FollowRepository) {
    delete("/api/following/unfollow") {
        val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }

        val didUserExist = followRepository.unfollowUserIfExists(
            request.followingUserId, request.followedUserId
        )
        if (didUserExist) {
            call.respond(
                HttpStatusCode.OK, BasicApiResponse(
                    successful = true,

                    )
            )
        } else {
            call.respond(
                HttpStatusCode.OK, BasicApiResponse(
                    successful = false, message = USER_NOT_FOUND
                )
            )
        }
    }
}