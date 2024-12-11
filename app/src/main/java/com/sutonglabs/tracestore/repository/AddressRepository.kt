package com.sutonglabs.tracestore.repository

import android.content.Context;
import com.sutonglabs.tracestore.models.Address;
import com.sutonglabs.tracestore.models.AddressResponse

interface AddressRepository {
    suspend fun getAddress(context: Context): AddressResponse? = null
}
