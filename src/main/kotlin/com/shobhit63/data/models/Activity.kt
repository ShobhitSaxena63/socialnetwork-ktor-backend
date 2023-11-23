package com.shobhit63.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Activity(
    @BsonId
    val id:String = ObjectId().toString(),
    val timeStamp:Long,
    val byUserId:String,
    val toUserId:String,
    val type:String,
    val parentId:String
)