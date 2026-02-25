package com.sutonglabs.tracestore.graphs.subcategory_graph

sealed class SubcategoryRoute(val route: String) {

    object Subcategory : SubcategoryRoute("subcategory/{parentId}") {
        const val PARENT_ID = "parentId"

        fun createRoute(parentId: Int) =
            "subcategory/$parentId"
    }
}