package com.shobhit63.data.response

import kotlinx.serialization.Serializable

data class BasicApiResponse(
    val successful:Boolean,
    val message:String? = null,
)
