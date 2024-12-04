package com.sutonglabs.tracestore.graphs.detail_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sutonglabs.tracestore.common.Constrains
import com.sutonglabs.tracestore.graphs.Graph
import com.sutonglabs.tracestore.ui.cart_screen.CartScreen
import com.sutonglabs.tracestore.ui.notification_screen.NotificationScreen
import com.sutonglabs.tracestore.ui.product_detail_screen.ProductDetailScreen

fun NavGraphBuilder.detailNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailScreen.ProductDetailScreen.route + "/{${Constrains.PRODUCT_ID_PARAM}}"
    ) {
        composable(DetailScreen.CartScreen.route) {
            CartScreen(onItemClick = { productId ->
                // Navigate to the detail screen or handle the item click logic
                navController.navigate("productDetail/$productId")
            })
        }
        composable(DetailScreen.NotificationScreen.route) {
            NotificationScreen()
        }
        composable(DetailScreen.ProductDetailScreen.route + "/{productId}") {
            ProductDetailScreen() {
                navController.popBackStack()
            }
        }
    }
}