package com.shobhit63.routes

import com.shobhit63.data.models.Activity
import com.shobhit63.data.requests.FollowUpdateRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.data.util.ActivityType
import com.shobhit63.repository.follow.FollowRepository
import com.shobhit63.service.ActivityService
import com.shobhit63.service.FollowService
import com.shobhit63.util.ApiResponseMessages.USER_NOT_FOUND
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.followUser(
    followService: FollowService,
    activityService: ActivityService
    ) {
    authenticate {
        post("/api/following/follow") {
            val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val didUserExist = followService.followUserIfExists(request,call.userId)

            if (didUserExist) {
                activityService.createActivity(
                    Activity(
                        timeStamp = System.currentTimeMillis(),
                        byUserId = call.userId,
                        toUserId = request.followedUserId,
                        type = ActivityType.FollowedUser.type,
                        parentId = "",
                    )
                )
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
}


fun Route.unfollowUser(followService: FollowService) {
    authenticate {
        delete("/api/following/unfollow") {
            val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val didUserExist = followService.unfollowUserIfExists(request,call.userId)
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
}