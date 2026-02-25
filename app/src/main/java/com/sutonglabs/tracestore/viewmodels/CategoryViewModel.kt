package com.sutonglabs.tracestore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.models.CategoryTree
import com.sutonglabs.tracestore.services.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import android.util.Log
import com.sutonglabs.tracestore.repository.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository(RetrofitInstance.api)

    var categories by mutableStateOf<List<CategoryTree>>(emptyList())
        private set

    var selectedCategory by mutableStateOf<CategoryTree?>(null)
        private set

    init {
        loadOnce()
    }

    private fun loadOnce() {
        viewModelScope.launch {
            try {
                val result = repository.getCategories()
                Log.d("CategoryVM", "Loaded ${result.size} parent categories")
                categories = result
            } catch (e: Exception) {
                Log.e("CategoryVM", "Failed to load categories", e)
            }
        }
    }

    fun selectCategory(category: CategoryTree) {
        selectedCategory = category
    }
}