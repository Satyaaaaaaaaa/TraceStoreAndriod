package com.sutonglabs.tracestore.models

data class CategoryTree(
    val id: Int,
    val name: String,
    val icon: String,
    val subcategories: List<SubCategory>,
    //val subcategories: List<CategoryTree> = emptyList() // For infinite nested categories -> Recursion
)

data class SubCategory(
    val id: Int,
    val name: String,
    val icon: String
)