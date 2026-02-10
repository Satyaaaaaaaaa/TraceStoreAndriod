package com.sutonglabs.tracestore.use_case

import com.sutonglabs.tracestore.models.assets.AssetVerificationResult
import com.sutonglabs.tracestore.repository.AssetRepository
import javax.inject.Inject

class ProcessQrScanUseCase @Inject constructor(
    private val repository: AssetRepository
) {
    suspend operator fun invoke(assetId: String): AssetVerificationResult {
        require(assetId.isNotBlank()) { "Invalid QR code" }
        return repository.verifyAsset(assetId)
    }
}

