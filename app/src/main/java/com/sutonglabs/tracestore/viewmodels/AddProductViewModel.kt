package com.sutonglabs.tracestore.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.models.ProductCreate
import com.sutonglabs.tracestore.repository.ProductRepository
import com.sutonglabs.tracestore.viewmodels.state.AddProductState
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    // -----------------------------
    // PRODUCT CREATION STATE
    // -----------------------------
    private val _state =
        mutableStateOf<AddProductState>(AddProductState.Idle)

    val state: State<AddProductState> = _state


    // ====================================================
    // ✅ IMAGE UPLOAD (STEP 1)
    // ====================================================
    fun uploadImages(
        context: Context,
        imageUris: List<Uri>,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    productRepository.uploadImages(
                        context,
                        imageUris
                    )

                onSuccess(response.image_uuids)

            } catch (e: Exception) {

                onError(
                    e.message ?: "Image upload failed"
                )
            }
        }
    }


    // ====================================================
    // ✅ CREATE PRODUCT (STEP 2)
    // ====================================================
    fun createProduct(
        product: ProductCreate
    ) {

        viewModelScope.launch {

            _state.value = AddProductState.Loading

            try {

                productRepository.addProduct(product)

                _state.value = AddProductState.Success

            } catch (e: Exception) {

                _state.value =
                    AddProductState.Error(
                        e.message ?: "Product creation failed"
                    )
            }
        }
    }
}