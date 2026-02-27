package com.sutonglabs.tracestore.ui.cart_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sutonglabs.tracestore.models.CartProduct
import com.sutonglabs.tracestore.ui.common.ProductCardImage
import com.sutonglabs.tracestore.viewmodels.CartViewModel

/* ------------------------------------------------ */
/* ---------------- CART SCREEN ------------------- */
/* ------------------------------------------------ */

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    navController: NavController
) {

    val cartState = cartViewModel.state.value

    val totalAmount =
        cartState.items.sumOf {
            it.product.price * it.quantity
        }

    val formattedTotal = "₹%.2f".format(totalAmount.toDouble())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = "My Cart",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        if (cartState.items.isEmpty()) {

            EmptyCart(navController)

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {

                items(
                    items = cartState.items,
                    key = { it.id }
                ) { item ->

                    ProductCard(
                        product = item.product,
                        quantity = item.quantity,
                        onItemClick = { onItemClick(item.product.id) },
                        onQuantityChange = {
                            cartViewModel.updateCartItem(item.id, it)
                        }
                    )
                }
            }

            Checkout(
                navController,
                formattedTotal
            )
        }
    }
}

/* ------------------------------------------------ */
/* ---------------- EMPTY CART -------------------- */
/* ------------------------------------------------ */

@Composable
fun EmptyCart(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "Your cart is empty",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("dashboard_screen") {
                    popUpTo("cart_screen") { inclusive = true }
                }
            }
        ) {
            Text("Continue Shopping")
        }
    }
}

/* ------------------------------------------------ */
/* ---------------- PRODUCT CARD ------------------ */
/* ------------------------------------------------ */

@Composable
fun ProductCard(
    product: CartProduct,
    quantity: Int,
    onItemClick: (Int) -> Unit,
    onQuantityChange: (Int) -> Unit,
) {

    Log.d("CART_DEBUG", "Product: ${product.name}")
    Log.d("CART_DEBUG", "Image: ${product.image}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onItemClick(product.id) },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /* ---------- PRODUCT IMAGE ---------- */

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                ProductCardImage(
                    imageUrl = product.image,
                    name = product.name,
                    height = 90.dp
                )
            }

            Spacer(Modifier.width(12.dp))

            /* ---------- PRODUCT DETAILS ---------- */

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "₹${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(10.dp))

                QuantitySelector(
                    quantity,
                    onQuantityChange
                )
            }
        }
    }
}

/* ------------------------------------------------ */
/* ------------ QUANTITY SELECTOR ----------------- */
/* ------------------------------------------------ */

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {

    Surface(
        tonalElevation = 3.dp,
        shape = MaterialTheme.shapes.medium
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {

            IconButton(
                onClick = {
                    if (quantity > 1)
                        onQuantityChange(quantity - 1)
                }
            ) {
                Icon(Icons.Default.Remove, null)
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = {
                    onQuantityChange(quantity + 1)
                }
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    }
}

/* ------------------------------------------------ */
/* ---------------- CHECKOUT ---------------------- */
/* ------------------------------------------------ */

@Composable
fun Checkout(
    navController: NavController,
    totalAmount: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = MaterialTheme.shapes.large
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text("Total")
                Text(
                    totalAmount,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = {
                    navController.navigate("checkout_screen")
                },
                shape = MaterialTheme.shapes.large
            ) {
                Text("Checkout")
            }
        }
    }
}