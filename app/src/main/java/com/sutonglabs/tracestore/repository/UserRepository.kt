package com.sutonglabs.tracestore.repository

import android.content.Context
import com.sutonglabs.tracestore.api.LoginRequest
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.data.saveJwtToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: TraceStoreAPI,
    private val context: Context
) {

    suspend fun login(username: String, password: String): Result<String> {
        val response = apiService.login(LoginRequest(username, password))
        return if (response.isSuccessful && response.body() != null) {
            val jwt = response.body()!!.data.token
            saveJwtToken(context, jwt)
            Result.success(jwt)
        } else {
            Result.failure(Exception("Login failed"))
        }
    }

    val jwtToken: Flow<String?> = getJwtToken(context)
}