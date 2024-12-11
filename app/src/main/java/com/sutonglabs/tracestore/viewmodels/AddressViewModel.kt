package com.sutonglabs.tracestore.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.common.Resource
import com.sutonglabs.tracestore.use_case.GetAddressUseCase
import com.sutonglabs.tracestore.viewmodels.state.AddressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase
    ): ViewModel() {
    private val _state = mutableStateOf(AddressState())
    val state: State<AddressState> = _state

    init {
        getAddress()
    }

    private fun getAddress() {
        getAddressUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = AddressState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = AddressState(address = result.data?.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = AddressState(errorMessage = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}