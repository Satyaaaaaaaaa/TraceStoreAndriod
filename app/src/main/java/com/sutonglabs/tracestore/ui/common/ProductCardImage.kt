package com.sutonglabs.tracestore.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sutonglabs.tracestore.common.Constants
import com.sutonglabs.tracestore.models.Product

@Composable
fun ProductCardImage(
    imageUrl: String?,
    name: String,
    height: Dp = 100.dp
) {

    if (!imageUrl.isNullOrEmpty()) {
        AsyncImage(
            model = Constants.BASE_URL + imageUrl,
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            contentAlignment = Alignment.Center
        ) {
            Text("No Image")
        }
    }
}
