package com.sutonglabs.tracestore.models
import com.google.gson.annotations.SerializedName

data class CartResponse(
    val status: Boolean,
    val data: CartData
)

data class CartData(
    val id: Int,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("CartItems")
    val cartItems: List<CartItem>
)

data class CartItem(
    val id: Int,
    val cartId: Int,
    val productId: Int,
    val quantity: Int,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("Product")
    val product: CartProduct
)

data class CartProduct(
    val id: Int,
    val name: String,
    val image: String,
    val price: Int
)
