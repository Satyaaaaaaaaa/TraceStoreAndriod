package com.sutonglabs.tracestore.ui.cart_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sutonglabs.tracestore.data.DemoCartItems
import com.sutonglabs.tracestore.models.CartItem
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun CartScreen(cartItems: List<CartItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Apply padding directly to LazyColumn
        verticalArrangement = Arrangement.spacedBy(8.dp) // Space between items
    ) {
        items(cartItems.size) { index ->
            CartItemCard(cartItem = cartItems[index])
        }
    }
}

@Composable
fun CartItemCard(cartItem: CartItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp), // Set a fixed height
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(cartItem.image),
                contentDescription = cartItem.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Price: ₹${cartItem.price}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Quantity: ${cartItem.quantity}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = { /* TODO: Implement remove functionality */ },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Remove")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(cartItems = DemoCartItems.items)
}
