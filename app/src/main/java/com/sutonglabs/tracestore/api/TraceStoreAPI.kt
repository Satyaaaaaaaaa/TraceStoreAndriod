package com.sutonglabs.tracestore.api

import com.sutonglabs.tracestore.models.CartResponse
import com.sutonglabs.tracestore.models.ProductResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(val username: String,
                        val password: String)
data class RegisterRequest(val username: String,
                           val email: String,
                           val firstName: String,
                           val lastName: String,
                           val age: String,
                           val GSTIN: String,
                           val password: String)

interface TraceStoreAPI {
    @POST("signup")
    suspend fun register(@Body registerRequest : RegisterRequest) : Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("product")
    fun getProducts() : Call<ProductResponse>

    @GET("cart")
    fun getCart(
        @Header("Authorization") token: String
    ) : Call<CartResponse>

}