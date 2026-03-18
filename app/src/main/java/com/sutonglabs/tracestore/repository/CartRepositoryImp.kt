package com.sutonglabs.tracestore.repository

import android.content.Context
import android.util.Log
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.api.UpdateCartRequest
import com.sutonglabs.tracestore.data.getJwtToken
import com.sutonglabs.tracestore.models.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI,
    @ApplicationContext private val context: Context
) : CartRepository {

    override suspend fun getCart(): CartResponse? {
        return try {
            val token = getJwtToken(context).first()
            Log.d("VIEW_CART", " getCart repository called")
            traceStoreApiService.getCart("Bearer $token")
        } catch (e: Exception) {
            Log.e("CartRepository", "getCart Exception", e)
            null
        }
    }

    override suspend fun updateCartItem(
        cartItemId: Int,
        quantity: Int
    ): UpdateCartResponse? {
        return try {
            val token = getJwtToken(context).first()
            traceStoreApiService.updateCartItem(
                "Bearer $token",
                UpdateCartRequest(cartItemId, quantity)
            )
        } catch (e: Exception) {
            Log.e("CartRepository", "updateCartItem Exception", e)
            null
        }
    }

    override suspend fun addToCart(productId: Int): CartResponse? {
        return try {
            val token = getJwtToken(context).first()
            traceStoreApiService.addToCart(
                AddToCartRequest(productId),
                "Bearer $token"
            )
        } catch (e: Exception) {
            Log.e("CartRepository", "addToCart Exception", e)
            null
        }
    }
}
