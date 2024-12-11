package com.sutonglabs.tracestore.models

data class AddressResponse(
    val status: Boolean,
    val data: List<Address>
)

data class Address(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val pincode: String,
    val city: String,
    val state: String,
    val locality: String,
    val buildingName: String,
    val landmark: String,
    val createdAt: String,
    val updatedAt: String,
    val userID: Int
)
