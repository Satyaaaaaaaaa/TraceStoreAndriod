package com.sutonglabs.tracestore.common

import com.sutonglabs.tracestore.models.CategoryTree
import android.util.Log


object CategoryCache {

    private var categories: List<CategoryTree>? = null

    fun get(): List<CategoryTree>? {
        Log.d("CategoryCache", "get() called. Cached = ${categories != null}")
        return categories
    }

    fun set(data: List<CategoryTree>) {
        Log.d("CategoryCache", "set() called. Size = ${data.size}")
        categories = data
    }

    fun clear() {
        Log.d("CategoryCache", "clear() called")
        categories = null
    }
}

