package com.shobhit63.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Post(
    val imageUrl:String,
    val userId:String,
    val timeStamp:Long,
    val description:String,
    @BsonId
    val id:String = ObjectId().toString(),
)