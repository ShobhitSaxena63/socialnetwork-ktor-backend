package com.shobhit63.routes

import com.shobhit63.data.requests.LikeUpdateRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.data.util.ParentType
import com.shobhit63.service.ActivityService
import com.shobhit63.service.LikeService
import com.shobhit63.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likeParent(
    likeService: LikeService,
    activityService: ActivityService
) {
    authenticate {
        post("/api/like") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId
            val likeSuccessful = likeService.likeParent(userId, request.parentId, request.parentType)
            if (likeSuccessful) {
                activityService.addLikeActivity(
                    byUserId = userId,
                    parentType = ParentType.fromType(request.parentType),
                    parentId = request.parentId
                )
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = false,
                        message = ApiResponseMessages.USER_NOT_FOUND
                    )
                )
            }
        }
    }
}

fun Route.unLikeParent(
    likeService: LikeService,
) {
    authenticate {
        delete("/api/unlike") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val unLikeSuccessful = likeService.unLikeParent(call.userId, request.parentId)
            if (unLikeSuccessful) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = false,
                        message = ApiResponseMessages.USER_NOT_FOUND
                    )
                )
            }
        }
    }
}