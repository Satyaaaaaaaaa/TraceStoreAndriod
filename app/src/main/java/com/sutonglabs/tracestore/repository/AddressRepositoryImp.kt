package com.sutonglabs.tracestore.repository

import android.content.Context
import android.util.Log
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.api.request_models.CreateAddressRequest
import com.sutonglabs.tracestore.api.request_models.UpdateAddressRequest
import com.sutonglabs.tracestore.api.response_model.CreateAddressResponse
import com.sutonglabs.tracestore.api.response_model.UpdateAddressResponse
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.models.AddressResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.await
import javax.inject.Inject

class AddressRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI,
): AddressRepository {
    override suspend fun getAddress(context: Context): AddressResponse? {
        return try {
            val token = getJwtToken(context).first()
            withContext(Dispatchers.IO) {
                traceStoreApiService.getAddress("Bearer $token").await()
            }
        } catch (e: Exception) {
            Log.e("AddressRepository", "getAddress Exception", e)
            null
        }
    }

    override suspend fun createAddress(context: Context, address: CreateAddressRequest): CreateAddressResponse? {
        return try {
            val token = getJwtToken(context).first()
            withContext(Dispatchers.IO) {
                val response = traceStoreApiService.createAddress("Bearer $token", address)
                if (response.isSuccessful && response.body() != null) {
                    Log.d("AddressRepository", "Address created successfully")
                    response.body()!!
                } else {
                    Log.e("AddressRepository", "Failed to create Address: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("AddressRepository", "createAddress Exception", e)
            null
        }
    }

    override suspend fun updateAddress(
        context: Context,
        updatedAddress: UpdateAddressRequest
    ): UpdateAddressResponse? {
        return try {
            val token = getJwtToken(context).first()
            withContext(Dispatchers.IO) {
                val response = traceStoreApiService.updateAddress("Bearer $token", updatedAddress)
                if (response.isSuccessful && response.body() != null) {
                    Log.d("AddressRepository", "Address updated successfully")
                    response.body()!!
                } else {
                    Log.e("AddressRepository", "Failed to update Address: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("AddressRepository", "updateAddress Exception", e)
            null
        }
    }
}
