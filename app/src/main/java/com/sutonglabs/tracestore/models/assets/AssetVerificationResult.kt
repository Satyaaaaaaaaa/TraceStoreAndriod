package com.sutonglabs.tracestore.models.assets

data class AssetVerificationResult(
    val assetId: String,
    val isAuthentic: Boolean,
    val timeline: List<AssetTimelineItem>
)