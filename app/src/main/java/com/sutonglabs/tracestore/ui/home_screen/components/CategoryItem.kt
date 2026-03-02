package com.sutonglabs.tracestore.ui.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sutonglabs.tracestore.common.category.CategoryIconMapper
import com.sutonglabs.tracestore.models.CategoryTree
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.sutonglabs.tracestore.common.Constants

@Composable
fun CategoryItem(
    category: CategoryTree,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .width(96.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("${Constants.BASE_URL}${category.icon}")
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = category.name,
            modifier = Modifier.size(36.dp)
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = category.name,
            maxLines = 1,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
