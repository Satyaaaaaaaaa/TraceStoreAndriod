package com.sutonglabs.tracestore.graphs.search_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.sutonglabs.tracestore.ui.search_screen.SearchScreen

//TODO: Hard code, create a new route builder file - clean arcitechture
//const val SEARCH_ROUTE = "search"
//private const val QUERY_ARG = "query"

fun NavGraphBuilder.searchNavGraph(
    navController: NavHostController
) {
    composable(
        route = SearchRoute.Search.route,
        arguments = listOf(
            navArgument(SearchRoute.Search.QUERY_ARG) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) { backStackEntry ->
        val query = backStackEntry.arguments?.getString(SearchRoute.Search.QUERY_ARG).orEmpty()

        SearchScreen(
            initialQuery = query,
            navController = navController
        )
    }
}
