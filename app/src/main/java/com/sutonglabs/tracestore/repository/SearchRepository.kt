package com.sutonglabs.tracestore.repository

import android.util.Log
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.api.response_model.SearchResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI
) {
    suspend fun search(
        query: String,
        page: Int,
        limit: Int
    ): SearchResponse? {
        return try {
            traceStoreApiService.searchProducts(query, page, limit)
        } catch (e: Exception) {
            Log.e("SearchRepository", "search Exception", e)
            null
        }
    }
}
