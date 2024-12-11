package com.sutonglabs.tracestore.use_case

import android.content.Context
import android.util.Log
import com.sutonglabs.tracestore.common.Resource
import com.sutonglabs.tracestore.models.Address
import com.sutonglabs.tracestore.models.AddressResponse
import com.sutonglabs.tracestore.models.CartResponse
import com.sutonglabs.tracestore.repository.AddressRepository
import com.sutonglabs.tracestore.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val repository: AddressRepository,
    private val context: Context,
){
    operator fun invoke(): Flow<Resource<AddressResponse>> = flow {
        try {
            emit(Resource.Loading())
            val address = repository.getAddress(context)
            emit(Resource.Success(address))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}