package com.sutonglabs.tracestore.repository

import com.sutonglabs.tracestore.models.CartResponse
import com.sutonglabs.tracestore.models.UpdateCartResponse

interface CartRepository {
    suspend fun getCart(): CartResponse?
    suspend fun addToCart(productId: Int): CartResponse?
    suspend fun updateCartItem(cartItemId: Int, quantity: Int): UpdateCartResponse?
}
