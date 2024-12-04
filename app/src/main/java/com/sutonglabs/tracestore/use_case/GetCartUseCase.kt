package com.sutonglabs.tracestore.use_case

import com.sutonglabs.tracestore.common.Resource
import com.sutonglabs.tracestore.models.CartResponse
import com.sutonglabs.tracestore.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import android.content.Context
import android.util.Log

class GetCartUseCase @Inject constructor(
    private val repository: CartRepository,
    private val context: Context,
){
    operator fun invoke(): Flow<Resource<CartResponse>> = flow {
        try {
            emit(Resource.Loading())
            val cart = repository.getCart(context)
            println(cart)
            Log.d("Cart: ", "$cart")
            emit(Resource.Success(cart))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}