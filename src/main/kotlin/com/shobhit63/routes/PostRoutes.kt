package com.shobhit63.routes

import com.shobhit63.data.requests.CreatePostRequest
import com.shobhit63.data.requests.DeletePostRequest
import com.shobhit63.data.response.BasicApiResponse
import com.shobhit63.service.CommentService
import com.shobhit63.service.LikeService
import com.shobhit63.service.PostService
import com.shobhit63.service.UserService
import com.shobhit63.util.ApiResponseMessages
import com.shobhit63.util.Constants.DEFAULT_POST_PAGE_SIZE
import com.shobhit63.util.QueryParams.PARAM_PAGE
import com.shobhit63.util.QueryParams.PARAM_PAGE_SIZE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPost(
    postService: PostService,
) {
    authenticate {
        post("/api/post/create") {
            val request = call.receiveOrNull<CreatePostRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId
            val didUserExist = postService.createPostIfUserExists(request, userId)
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


fun Route.getPostsForFollows(
    postService: PostService,
) {
    authenticate {
        get("api/post/get") {
            val page = call.parameters[PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[PARAM_PAGE_SIZE]?.toIntOrNull() ?: DEFAULT_POST_PAGE_SIZE
            val posts = postService.getPostsForFollows(call.userId, page, pageSize)
            call.respond(
                HttpStatusCode.OK,
                posts
            )
        }
    }

}

fun Route.deletePost(
    postService: PostService,
    likeService: LikeService,
    commentService: CommentService
) {
    authenticate {
        delete("/api/post/delete") {
            val request = call.receiveOrNull<DeletePostRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val post = postService.getPost(request.postId)
            if (post == null) {
                call.respond(
                    HttpStatusCode.NotFound
                )
                return@delete
            }
            if (post.userId == call.userId) {
                postService.deletePost(request.postId)
                likeService.deleteLikesForParent(request.postId)
                //TODO: Delete comments from post
                commentService.deleteCommentsFromPost(request.postId)
                call.respond(HttpStatusCode.OK)

            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }


        }
    }
}
