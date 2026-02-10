package com.sutonglabs.tracestore.viewmodels.state

import com.sutonglabs.tracestore.models.assets.AssetVerificationResult

data class QrScannerState(
    val loading: Boolean = false,
    val result: AssetVerificationResult? = null,
    val error: String? = null
)
