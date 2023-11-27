package com.shobhit63.routes

import com.shobhit63.data.models.Post
import com.shobhit63.data.requests.CreatePostRequest
import com.shobhit63.data.requests.FollowUpdateRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.plugins.email
import com.shobhit63.repository.post.PostRepository
import com.shobhit63.service.PostService
import com.shobhit63.service.UserService
import com.shobhit63.util.ApiResponseMessages
import com.shobhit63.util.Constants.DEFAULT_POST_PAGE_SIZE
import com.shobhit63.util.QueryParams.PARAM_PAGE
import com.shobhit63.util.QueryParams.PARAM_PAGE_SIZE
import com.shobhit63.util.QueryParams.PARAM_USER_ID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPostRoute(
    postService: PostService,
    userService: UserService
) {
    authenticate {
        post("/api/post/create") {
            val request = call.receiveOrNull<CreatePostRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            ifEmailBelongsToUser(
                userId = request.userId,
                validateEmail = userService::doesEmailBelongsToUserId
            ) {
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
    }
}


fun Route.getPostsForFollows(
    postService: PostService,
    userService: UserService
) {
    authenticate {
        get() {
            val userId = call.parameters[PARAM_USER_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val page = call.parameters[PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[PARAM_PAGE_SIZE]?.toIntOrNull() ?: DEFAULT_POST_PAGE_SIZE

            ifEmailBelongsToUser(
                userId = userId,
                validateEmail = userService::doesEmailBelongsToUserId
            ) {
                val posts = postService.getPostsForFollows(userId, page, pageSize)
                call.respond(
                    HttpStatusCode.OK,
                    posts
                )
            }
        }
    }

}