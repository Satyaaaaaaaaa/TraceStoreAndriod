package com.sutonglabs.tracestore.graphs.search_graph

sealed class SearchRoute(val route: String) {

    object Search : SearchRoute("search?query={query}") {
        const val QUERY_ARG = "query"

        fun createRoute(query: String? = null): String {
            return if (query.isNullOrBlank()) {
                "search"
            } else {
                "search?$QUERY_ARG=$query"
            }
        }
    }
}
