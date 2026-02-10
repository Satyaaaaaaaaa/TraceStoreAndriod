package com.sutonglabs.tracestore.ui.qr_scanner_screen

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.sutonglabs.tracestore.common.qr.QrAnalyzer
import com.sutonglabs.tracestore.models.assets.AssetVerificationResult
import com.sutonglabs.tracestore.viewmodels.QrScannerViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.sutonglabs.tracestore.graphs.detail_graph.DetailScreen
import com.sutonglabs.tracestore.graphs.home_graph.ShopHomeScreen

@Composable
fun QrScannerScreen(
    navController: NavController,
    viewModel: QrScannerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner

    // ✅ Navigate ONLY when backend result arrives
    LaunchedEffect(state.result) {
        state.result?.let { result ->
            navController.navigate(DetailScreen.AssetDetailScreen.route + "/${state.result!!.assetId}") {
                popUpTo(ShopHomeScreen.QrScannerScreen.route) { inclusive = true }
            }
        }
    }

    when {
        state.loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error!!, color = Color.Red)
            }
        }

        else -> {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    val previewView = PreviewView(context)

                    val cameraProviderFuture =
                        ProcessCameraProvider.getInstance(context)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val analyzer = ImageAnalysis.Builder()
                            .setBackpressureStrategy(
                                ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                            )
                            .build()
                            .also {
                                it.setAnalyzer(
                                    ContextCompat.getMainExecutor(context),
                                    QrAnalyzer { qr ->
                                        viewModel.onQrScanned(qr)
                                    }
                                )
                            }

                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            analyzer
                        )

                    }, ContextCompat.getMainExecutor(context))

                    previewView
                }
            )
        }
    }
}



