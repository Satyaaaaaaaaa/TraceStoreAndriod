package com.sutonglabs.tracestore.repository

import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.models.assets.AssetVerificationResult
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val api: TraceStoreAPI
) {
    suspend fun verifyAsset(assetId: String): AssetVerificationResult {
        return api.verifyAsset(mapOf("asset_id" to assetId))
    }
}

