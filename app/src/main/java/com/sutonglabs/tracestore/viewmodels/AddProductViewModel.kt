package com.sutonglabs.tracestore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sutonglabs.tracestore.models.Product
import com.sutonglabs.tracestore.repository.ProductRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import okhttp3.MultipartBody

class AddProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _addProductStatus = mutableStateOf<Boolean?>(null)
    val addProductStatus: State<Boolean?> = _addProductStatus

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val result = productRepository.addProduct(product)
            _addProductStatus.value = result != null
        }
    }

    // New method to upload an image and create a product with the uploaded image path
    fun uploadImageAndCreateProduct(image: MultipartBody.Part, product: Product) {
        viewModelScope.launch {
            val imageUploadResponse = productRepository.uploadImage(image)
            if (imageUploadResponse != null) {
                product.image = imageUploadResponse.path // Use the image path in the product model
                addProduct(product)
            }
        }
    }
}
