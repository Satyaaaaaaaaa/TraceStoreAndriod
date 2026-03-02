package com.sutonglabs.tracestore.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.api.request_models.CreateAddressRequest
import com.sutonglabs.tracestore.api.request_models.UpdateAddressRequest
import com.sutonglabs.tracestore.common.Resource
import com.sutonglabs.tracestore.repository.AddressRepository
import com.sutonglabs.tracestore.repository.PincodeRepository
import com.sutonglabs.tracestore.use_case.GetAddressUseCase
import com.sutonglabs.tracestore.viewmodels.state.AddressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val addressRepository: AddressRepository,
    private val pincodeRepository: PincodeRepository
) : ViewModel() {

    // ================= ADDRESS LIST =================

    private val _state = mutableStateOf(AddressState())
    val state: State<AddressState> = _state

    init {
        getAddress()
    }

    fun getAddress() {
        getAddressUseCase().onEach { result ->
            when (result) {

                is Resource.Loading ->
                    _state.value = AddressState(isLoading = true)

                is Resource.Success ->
                    _state.value =
                        AddressState(address = result.data?.data ?: emptyList())

                is Resource.Error ->
                    _state.value =
                        AddressState(errorMessage = result.message ?: "Error")
            }
        }.launchIn(viewModelScope)
    }

    // ================= PINCODE AUTOFILL =================

    var city = mutableStateOf("")
        private set

    var stateName = mutableStateOf("")
        private set

    var isFetchingLocation = mutableStateOf(false)
        private set

    private var pincodeJob: Job? = null

    fun onPincodeChanged(pincode: String) {

        if (pincode.length != 6) {
            city.value = ""
            stateName.value = ""
            return
        }

        pincodeJob?.cancel()

        pincodeJob = viewModelScope.launch {

            delay(500)

            try {
                isFetchingLocation.value = true

                val response =
                    pincodeRepository.fetchLocation(pincode)

                city.value = response.data.city
                stateName.value = response.data.state

            } catch (e: Exception) {
                Log.e("PINCODE", e.message ?: "")
            } finally {
                isFetchingLocation.value = false
            }
        }
    }

    // ================= CREATE ADDRESS =================

    fun createAddress(
        request: CreateAddressRequest,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                addressRepository.createAddress(context, request)
                Toast.makeText(context,"Address Created!",Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("AddressVM", e.message ?: "")
            }
        }
    }

    // ================= UPDATE ADDRESS =================

    fun updateAddress(
        request: UpdateAddressRequest,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                addressRepository.updateAddress(context, request)
                Toast.makeText(context,"Address Updated!",Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("AddressVM", e.message ?: "")
            }
        }
    }
}