package com.sutonglabs.tracestore.viewmodels.state

import com.sutonglabs.tracestore.models.Address


data class AddressState(
    val isLoading: Boolean = false,
    val address: List<Address>? = null,
    val errorMessage: String = ""
)