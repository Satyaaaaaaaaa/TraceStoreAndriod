package com.sutonglabs.tracestore.repository

import android.content.Context
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.models.CartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.await
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI,
): CartRepository {
    override suspend fun getCart(context: Context): CartResponse {
        val token = getJwtToken(context).first()
        return withContext(Dispatchers.IO) {
            traceStoreApiService.getCart("Bearer $token").await()
        }
    }
}