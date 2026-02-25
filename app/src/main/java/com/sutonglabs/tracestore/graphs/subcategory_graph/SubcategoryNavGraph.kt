package com.sutonglabs.tracestore.graphs.subcategory_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sutonglabs.tracestore.graphs.subcategory_graph.SubcategoryRoute
import com.sutonglabs.tracestore.ui.subcategory_screen.SubcategoryScreen

fun NavGraphBuilder.subcategoryNavGraph(
    navController: NavHostController
) {

    composable(
        route = SubcategoryRoute.Subcategory.route,
        arguments = listOf(
            navArgument(SubcategoryRoute.Subcategory.PARENT_ID) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->

        val parentId =
            backStackEntry.arguments
                ?.getInt(SubcategoryRoute.Subcategory.PARENT_ID) ?: 0

        SubcategoryScreen(
            parentId = parentId,
            navController = navController
        )
    }
}