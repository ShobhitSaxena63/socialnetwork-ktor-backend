package com.shobhit63.routes

import com.shobhit63.data.models.Post
import com.shobhit63.data.requests.CreatePostRequest
import com.shobhit63.data.requests.FollowUpdateRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.repository.post.PostRepository
import com.shobhit63.service.PostService
import com.shobhit63.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPostRoute(postService: PostService) {
    post("/api/post/create") {
        val request = call.receiveOrNull<CreatePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val didUserExist = postService.createPostIfUserExists(request)
        if (!didUserExist) {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = false,
                    message = ApiResponseMessages.USER_NOT_FOUND
                )
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = true,
                )
            )
        }

    }
}