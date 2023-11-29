package com.shobhit63.data.requests

import com.shobhit63.data.util.ParentType

data class LikeUpdateRequest(
    val parentId:String,
    val parentType: Int
)
