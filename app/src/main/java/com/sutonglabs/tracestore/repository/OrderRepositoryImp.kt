package com.sutonglabs.tracestore.repository

import android.content.Context
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.api.request_models.CreateOrderRequest
import com.sutonglabs.tracestore.api.response_model.CreateOrderResponse
import com.sutonglabs.tracestore.api.response_model.Order
import com.sutonglabs.tracestore.api.response_model.OrderItem
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.models.SellerOrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log
import android.widget.Toast

@Singleton
class OrderRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI
) : OrderRepository {

    override suspend fun createOrder(context: Context, orderRequest: CreateOrderRequest): CreateOrderResponse? {
        return try {
            val token = getJwtToken(context).first()
            withContext(Dispatchers.IO) {
                val response = traceStoreApiService.createOrder("Bearer $token", orderRequest)
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!
                } else {
                    Log.e("OrderRepository", "Failed to create order: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("OrderRepository", "createOrder Exception", e)
            null
        }
    }

    override suspend fun getOrders(context: Context): List<Order>? {
        return try {
            val token = getJwtToken(context).first()
            withContext(Dispatchers.IO) {
                val response = traceStoreApiService.getOrders("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.data
                } else {
                    Log.e("OrderRepository", "Failed to fetch orders: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("OrderRepository", "getOrders Exception", e)
            null
        }
    }

    override suspend fun getSellerOrders(context: Context): List<SellerOrderResponse>? {
        return try {
            val token = getJwtToken(context).first()
            withContext(Dispatchers.IO) {
                val response = traceStoreApiService.getSellerOrders("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.data
                } else {
                    Log.e("OrderRepository", "Failed to fetch seller orders: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("OrderRepository", "getSellerOrders Exception", e)
            null
        }
    }

    override suspend fun updateOrderStatus(
        context: Context,
        orderId: Int,
        status: String
    ) {
        try {
            val rawToken = getJwtToken(context).first()
                ?: throw IllegalStateException("JWT token is null")

            val token = "Bearer $rawToken"

            val response = traceStoreApiService.updateOrderStatus(
                token = token,
                orderId = orderId,
                body = mapOf("status" to status)
            )

            if (!response.isSuccessful) {
                Log.e("OrderRepository", "Failed to update order status: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("OrderRepository", "updateOrderStatus Exception", e)
        }
    }
    
}
