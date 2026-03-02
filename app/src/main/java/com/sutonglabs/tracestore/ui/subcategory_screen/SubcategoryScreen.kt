package com.sutonglabs.tracestore.ui.subcategory_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sutonglabs.tracestore.common.Constants
import com.sutonglabs.tracestore.graphs.search_graph.SearchRoute
import com.sutonglabs.tracestore.viewmodels.CategoryViewModel

/* ========================================================= */
/* ================= SVG NETWORK ICON ====================== */
/* ========================================================= */

@Composable
fun NetworkSvgIcon(
    iconPath: String,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    // Build full URL safely
    val fullUrl =
        if (iconPath.startsWith("http"))
            iconPath
        else
            "${Constants.BASE_URL}$iconPath"

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(fullUrl)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = null,
        modifier = modifier
    )
}

/* ========================================================= */
/* ================= SUBCATEGORY SCREEN ==================== */
/* ========================================================= */

@Composable
fun SubcategoryScreen(
    parentId: Int,
    navController: NavHostController,
    viewModel: CategoryViewModel = viewModel()
) {

    val categories = viewModel.categories
    val selectedCategory = viewModel.selectedCategory

    /* ---------- Auto Select Parent Category ---------- */

    LaunchedEffect(categories) {
        if (categories.isNotEmpty()) {
            categories.find { it.id == parentId }
                ?.let { viewModel.selectCategory(it) }
        }
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        /* ================================================= */
        /* =============== LEFT PANEL ====================== */
        /* ================================================= */

        LazyColumn(
            modifier = Modifier
                .weight(0.38f)
                .fillMaxHeight()
                .background(Color(0xFFF5F5F5))
        ) {

            items(categories) { category ->

                val isSelected =
                    selectedCategory?.id == category.id

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected)
                                Color.White
                            else
                                Color.Transparent
                        )
                        .clickable {
                            viewModel.selectCategory(category)
                        }
                        .padding(
                            horizontal = 12.dp,
                            vertical = 14.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    NetworkSvgIcon(
                        iconPath = category.icon,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = category.name,
                        color =
                            if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        /* ================================================= */
        /* =============== RIGHT PANEL ===================== */
        /* ================================================= */

        LazyColumn(
            modifier = Modifier
                .weight(0.62f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {

            selectedCategory?.subcategories?.let { subCategories ->

                items(subCategories) { subCategory ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    SearchRoute.Search
                                        .createRoute(subCategory.name)
                                )
                            }
                            .padding(vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        NetworkSvgIcon(
                            iconPath = subCategory.icon,
                            modifier = Modifier.size(26.dp)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = subCategory.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}