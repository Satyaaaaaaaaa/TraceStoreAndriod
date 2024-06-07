package com.sutonglabs.tracestore.api

data class LoginResponse(
    val status: Boolean,
    val data: Data
)

data class Data(
    val user: User,
    val token: String
)

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val role: String,
    val gstin: String?,
    val createdAt: String,
    val updatedAt: String
)