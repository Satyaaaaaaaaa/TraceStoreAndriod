package com.sutonglabs.tracestore.repository

import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.common.CategoryCache
import com.sutonglabs.tracestore.models.CategoryTree

import android.util.Log

class CategoryRepository(
    private val traceStoreApiService: TraceStoreAPI
) {

    suspend fun getCategories(): List<CategoryTree> {

        CategoryCache.get()?.let {
            Log.d("CategoryRepository", "Returning categories from CACHE")
            return it
        }

        return try {
            Log.d("CategoryRepository", "Fetching categories from BACKEND")

            val response = traceStoreApiService.getCategories()

            if (response.isSuccessful && response.body() != null) {

                val categories = response.body()!!.categories

                Log.d("CategoryRepository", "Fetched ${categories.size} parent categories")

                CategoryCache.set(categories)
                categories
            } else {
                Log.e("CategoryRepository", "API error: ${response.code()}")
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("CategoryRepository", "Failed to fetch categories", e)
            emptyList()
        }
    }
}
