package com.shobhit63.data.requests

import kotlinx.serialization.Serializable


data class CreateAccountRequest(
    val email:String,
    val username:String,
    val password:String
)
