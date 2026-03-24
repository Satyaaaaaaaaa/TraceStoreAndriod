package com.sutonglabs.tracestore.repository

import android.content.Context
import android.util.Log
import com.sutonglabs.tracestore.api.GetUserResponse
import com.sutonglabs.tracestore.api.LoginRequest
import com.sutonglabs.tracestore.api.RegisterRequest
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.api.request_models.UpdateUserRequest
import com.sutonglabs.tracestore.models.User
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.data.saveJwtToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: TraceStoreAPI,
    private val context: Context
) {
    val jwtToken: Flow<String?> = getJwtToken(context)

    suspend fun getUserInfo(token: String): Result<User> {
        return try {
            val response = apiService.getUserInfo("Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                val responseData = response.body()!!
                val apiUser = responseData.data

                Log.d("UserRepository", "Extracted API User: $apiUser")

                val mappedUser = User(
                    id = apiUser.id.toInt(),
                    username = apiUser.username ?: "Unknown",
                    email = apiUser.email ?: "No Email",
                    firstName = apiUser.firstName ?: "",
                    lastName = apiUser.lastName ?: "",
                    age = apiUser.age.toInt(),
                    role = apiUser.role ?: "User",
                    gstin = apiUser.gstin,
                    createdAt = apiUser.createdAt,
                    updatedAt = apiUser.updatedAt,
                    blockchainStatus = apiUser.blockchainStatus,
                )

                Result.success(mappedUser)
            } else {
                Result.failure(Exception("Failed to fetch user info: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(): Result<Int> {
        return try {
            val token = getJwtToken(context).first()
            val response = apiService.getUser("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data.id)
            } else {
                Result.failure(Exception("Failed to fetch user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful && response.body() != null) {
                val jwt = response.body()!!.data.token
                saveJwtToken(context, jwt)
                Log.d("UserRepository", "Saved JWT Token: $jwt")
                Result.success(jwt)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Login failed"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Login Exception", e)
            Result.failure(Exception("Network error: Server not responding"))
        }
    }

    suspend fun register(username: String,
                         email: String,
                         firstName: String,
                         lastName: String,
                         age: String,
                         GSTIN: String,
                         password: String
    ): Result<String> {
        return try {
            val response = apiService.register(RegisterRequest(username, email, firstName, lastName, age, GSTIN, password))
            if (response.isSuccessful && response.body() != null) {
                val jwt = response.body()!!.data.token
                saveJwtToken(context, jwt)
                Log.d("UserRepository", "Saved JWT Token: $jwt")
                Result.success(jwt)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Registration Failed!"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Register Exception", e)
            Result.failure(Exception("Network error: Server not responding"))
        }
    }

    suspend fun clearJwtToken(context: Context) {
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit().clear().apply()
        Log.d("UserRepository", "JWT Token cleared.")
    }

    suspend fun updateUser(token: String, firstName: String, lastName: String, age: Int): Result<User> {
        return try {
            val request = UpdateUserRequest(firstName, lastName, age)
            val response = apiService.updateUser(request, "Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                Log.d("UserRepository", "User profile updated: ${response.body()!!.data}")
                Result.success(response.body()!!.data)
            } else {
                Log.d("UserRepository", "Failed to update user profile")
                Result.failure(Exception("Update failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun syncUserToBlockchain(token: String): Result<User> {
        return try {
            val response = apiService.syncUserToBlockchain("Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                val updatedUser = response.body()!!.data
                Result.success(updatedUser)
            } else {
                Result.failure(Exception("Sync Failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
