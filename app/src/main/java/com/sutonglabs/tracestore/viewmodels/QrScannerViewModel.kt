package com.sutonglabs.tracestore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.use_case.ProcessQrScanUseCase
import com.sutonglabs.tracestore.viewmodels.state.QrScannerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val processQrScanUseCase: ProcessQrScanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(QrScannerState())
    val state = _state.asStateFlow()

    private var scanInProgress = false

    fun onQrScanned(assetId: String) {
        if (scanInProgress) return
        scanInProgress = true

        viewModelScope.launch {
            _state.value = QrScannerState(loading = true)
            try {
                val result = processQrScanUseCase(assetId)
                _state.value = QrScannerState(result = result)
            } catch (e: Exception) {
                scanInProgress = false
                _state.value = QrScannerState(
                    error = e.message ?: "Asset verification failed"
                )
            }
        }
    }

//    fun reset() {
//        scanInProgress = false
//        _state.value = QrScannerState()
//    }
//
//    fun consumeResult() {
//        _state.value = _state.value.copy(result = null)
//    }

}


