package com.sutonglabs.tracestore.repository

import android.content.Context
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.models.Address
import com.sutonglabs.tracestore.models.AddressResponse
import com.sutonglabs.tracestore.models.CartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.await
import javax.inject.Inject

class AddressRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI,
): AddressRepository {
    override suspend fun getAddress(context: Context): AddressResponse {
        val token = getJwtToken(context).first()
        return withContext(Dispatchers.IO) {
            traceStoreApiService.getAddress("Bearer $token").await()
        }
    }
}