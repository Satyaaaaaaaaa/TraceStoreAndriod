package com.sutonglabs.tracestore.repository

import com.sutonglabs.tracestore.api.TraceStoreAPI
import javax.inject.Inject

class TraceStoreRepository @Inject constructor(private val  apiService: TraceStoreAPI) {
    suspend fun login(username: String, password: String) {

    }
}