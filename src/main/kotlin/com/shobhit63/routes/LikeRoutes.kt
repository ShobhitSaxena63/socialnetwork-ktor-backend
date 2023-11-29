package com.shobhit63.routes

import com.shobhit63.data.requests.LikeUpdateRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.service.LikeService
import com.shobhit63.service.UserService
import com.shobhit63.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likeParent(
    likeService: LikeService,
) {
    authenticate {
        post("/api/like") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
                val likeSuccessful = likeService.likeParent(call.userId, request.parentId)
                if (likeSuccessful) {
                    call.respond(HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    ))
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
                    call.respond(HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    ))
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