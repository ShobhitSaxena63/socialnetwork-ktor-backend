package com.shobhit63.data.requests

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CreatePostRequest(
    val description:String
)