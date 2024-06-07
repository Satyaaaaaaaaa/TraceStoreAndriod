package com.sutonglabs.tracestore.api

import com.sutonglabs.tracestore.models.ProductResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)

interface TraceStoreAPI {

    @POST("register")
    fun register(@Header("") someVar : String) : Response<String>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("product")
    fun getProducts() : Call<ProductResponse>
}