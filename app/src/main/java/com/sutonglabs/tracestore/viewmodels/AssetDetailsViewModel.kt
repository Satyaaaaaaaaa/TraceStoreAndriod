package com.sutonglabs.tracestore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.models.assets.AssetVerificationResult
import com.sutonglabs.tracestore.repository.AssetRepository
import com.sutonglabs.tracestore.viewmodels.state.AssetDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetDetailsViewModel @Inject constructor(
    private val repository: AssetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AssetDetailsState())
    val state = _state.asStateFlow()

    fun loadAsset(assetId: String) {
        viewModelScope.launch {
            _state.value = AssetDetailsState(loading = true)

            try {
                val asset = repository.verifyAsset(assetId)
                _state.value = AssetDetailsState(asset = asset)
            } catch (e: Exception) {
                _state.value = AssetDetailsState(
                    error = e.message ?: "Failed to load asset"
                )
            }
        }
    }
}
