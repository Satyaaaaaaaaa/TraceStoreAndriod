package com.sutonglabs.tracestore.api.response_model

data class PincodeResponse(
    val success: Boolean,
    val data: LocationData
)

data class LocationData(
    val city: String,
    val state: String,
    val country: String
)