package com.sutonglabs.tracestore.ui.asset_details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sutonglabs.tracestore.viewmodels.AssetDetailsViewModel

@Composable
fun AssetDetailsScreen(
    assetId: String,
    popBack: () -> Unit,
    viewModel: AssetDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(assetId) {
        viewModel.loadAsset(assetId)
    }

    when {
        state.loading -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
            )
        }

        state.error != null -> {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        state.asset != null -> {
            val asset = state.asset!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Asset Verification",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Asset ID: ${asset.assetId}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = if (asset.isAuthentic) "Status: Authentic ✅" else "Status: Fake ❌",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Timeline",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )

                LazyColumn {
                    items(asset.timeline) { item ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(text = "Stage: ${item.stage}")
                            Text(text = "Location: ${item.location}")
                            Text(text = "Time: ${item.timestamp}")
                        }
                    }
                }
            }
        }
    }
}
