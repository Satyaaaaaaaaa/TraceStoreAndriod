package com.sutonglabs.tracestore.viewmodels.state

import com.sutonglabs.tracestore.models.assets.AssetVerificationResult

data class AssetDetailsState(
    val loading: Boolean = false,
    val asset: AssetVerificationResult? = null,
    val error: String? = null
)
