package com.sutonglabs.tracestore.ui.subcategory_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sutonglabs.tracestore.graphs.search_graph.SearchRoute
import com.sutonglabs.tracestore.models.CategoryTree
import com.sutonglabs.tracestore.models.SubCategory
import com.sutonglabs.tracestore.viewmodels.CategoryViewModel

@Composable
fun SubcategoryScreen(
    parentId: Int,
    navController: NavHostController,
    viewModel: CategoryViewModel = viewModel()
) {

    val categories = viewModel.categories
    val selectedCategory = viewModel.selectedCategory

    // Auto select parent when screen opens
    LaunchedEffect(categories) {
        if (categories.isNotEmpty()) {
            val initial = categories.find { it.id == parentId }
            initial?.let { viewModel.selectCategory(it) }
        }
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        // ================= LEFT SIDE (Parent Categories) =================
        LazyColumn(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .background(Color(0xFFF5F5F5))
        ) {

            items(categories) { parent ->

                val isSelected =
                    selectedCategory?.id == parent.id

                Text(
                    text = parent.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected) Color.White
                            else Color.Transparent
                        )
                        .clickable {
                            viewModel.selectCategory(parent)
                        }
                        .padding(16.dp),
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // ================= RIGHT SIDE (Subcategories) =================
        LazyColumn(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {

            selectedCategory?.subcategories?.let { subCategories ->

                items(subCategories) { subCategory ->

                    Text(
                        text = subCategory.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                navController.navigate(
                                    SearchRoute.Search
                                        .createRoute(subCategory.name)
                                )
                            }
                            .padding(vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}