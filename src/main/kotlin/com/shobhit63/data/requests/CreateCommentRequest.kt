package com.shobhit63.data.requests

data class CreateCommentRequest(
    val comment: String,
    val postId:String,
)