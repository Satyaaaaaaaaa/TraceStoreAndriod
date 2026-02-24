package com.sutonglabs.tracestore.api.response_model

import com.sutonglabs.tracestore.models.CategoryTree

data class CategoryTreeResponse(
    val status: Boolean,
    val categories: List<CategoryTree>
)
