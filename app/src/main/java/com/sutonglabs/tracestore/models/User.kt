package com.sutonglabs.tracestore.models

data class User(
    val id: Int,
    val username: String = "",
    val email: String = ""
)

data class LoginUser(
    val username: String = "",
    val password: String = ""
)