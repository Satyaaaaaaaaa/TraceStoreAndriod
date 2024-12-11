package com.sutonglabs.tracestore.graphs.cart_graph

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sutonglabs.tracestore.graphs.Graph
import com.sutonglabs.tracestore.ui.cart_screen.CartScreen
import com.sutonglabs.tracestore.ui.checkout_screen.CheckoutScreen


fun NavGraphBuilder.cartNavGraph(navController: NavHostController) {
    navigation(
        startDestination = "cart_screen",
        route = Graph.CART
    ) {
        composable("cart_screen") {
            CartScreen(
                navController = navController,
                onItemClick = { productId ->
                    println("Clicked product ID: $productId") // Handle item click
                }
            )
        }

        composable(CartScreen.CheckoutScreen.route) {
            CheckoutScreen()
        }

    }

}