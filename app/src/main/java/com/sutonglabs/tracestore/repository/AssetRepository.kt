package com.sutonglabs.tracestore.repository

import android.util.Log
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.models.assets.AssetVerificationResult
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val api: TraceStoreAPI
) {
    suspend fun verifyAsset(assetId: String): AssetVerificationResult? {
        return try {
            api.verifyAsset(mapOf("asset_id" to assetId))
        } catch (e: Exception) {
            Log.e("AssetRepository", "verifyAsset Exception", e)
            null
        }
    }
}
