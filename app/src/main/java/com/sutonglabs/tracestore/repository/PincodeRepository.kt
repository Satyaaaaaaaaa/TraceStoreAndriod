package com.sutonglabs.tracestore.repository

import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.api.response_model.PincodeResponse
import javax.inject.Inject

class PincodeRepository @Inject constructor(
    private val api: TraceStoreAPI
) {

    suspend fun fetchLocation(
        pincode: String
    ): PincodeResponse {

        return api.getLocationFromPincode(pincode)

    }
}