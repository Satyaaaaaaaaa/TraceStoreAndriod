package com.sutonglabs.tracestore.common

import com.sutonglabs.tracestore.models.CartItem
import com.sutonglabs.tracestore.models.CartProduct

data class CartProductState(
    val isLoading: Boolean = false,
    val product: List<CartItem>? = null,
    val errorMessage: String = ""
)
