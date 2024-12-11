package com.sutonglabs.tracestore.graphs.cart_graph

import com.sutonglabs.tracestore.graphs.detail_graph.DetailScreen


sealed class CartScreen(val route: String) {
    data object CheckoutScreen : CartScreen("checkout_screen")

}