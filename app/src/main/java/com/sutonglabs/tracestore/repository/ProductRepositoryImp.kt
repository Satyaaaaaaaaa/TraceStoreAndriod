package com.sutonglabs.tracestore.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.data.auth.TokenProvider
import com.sutonglabs.tracestore.models.*
import com.sutonglabs.tracestore.viewmodels.helper.ImageFileHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI,
    private val tokenProvider: TokenProvider
) : ProductRepository {

    override suspend fun getProduct(): ProductResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = traceStoreApiService.getProducts()

                if (response.isSuccessful && response.body() != null) {
                    response.body()!!
                } else {
                    Log.e(
                        "ProductRepository",
                        "Fetch products failed: ${response.code()} ${response.message()}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("ProductRepository", "getProduct Exception", e)
                null
            }
        }

    override suspend fun getProductDetail(id: Int): Product? =
        withContext(Dispatchers.IO) {
            try {
                val token = tokenProvider.getToken()
                val response =
                    traceStoreApiService.getProductDetail(id, "Bearer $token")

                if (response.isSuccessful && response.body()?.data != null) {
                    response.body()!!.data!!
                } else {
                    Log.e(
                        "ProductRepository",
                        "Fetch product detail failed: ${response.code()}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("ProductRepository", "getProductDetail Exception", e)
                null
            }
        }

    override suspend fun addProduct(product: ProductCreate): ProductDetailResponse? =
        withContext(Dispatchers.IO) {
            try {
                val token = tokenProvider.getToken()
                val response =
                    traceStoreApiService.addProduct("Bearer $token", product)

                val body = response.body()

                if (response.isSuccessful && body != null) {
                    Log.d("ProductRepository", "Product creation successful")
                    body
                } else {
                    Log.e(
                        "ProductRepository",
                        "Add product failed: ${response.code()} ${response.message()}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("ProductRepository", "addProduct Exception", e)
                null
            }
        }


    override suspend fun uploadImages(
        context: Context,
        imageUris: List<Uri>
    ): ImageUploadResponse? {
        return try {
            val token = tokenProvider.getToken()

            val parts = imageUris.map { uri ->
                val file = ImageFileHelper.uriToFile(context, uri)
                val body = file
                    .asRequestBody("image/*".toMediaTypeOrNull())

                MultipartBody.Part.createFormData(
                    name = "image",
                    filename = file.name,
                    body = body
                )
            }

            val response =
                traceStoreApiService.uploadProductImages(
                    "Bearer $token",
                    parts
                )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                Log.e("ProductRepository", "Image upload failed: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "uploadImages Exception", e)
            null
        }
    }

    override suspend fun syncProductToBlockchain(productId: Int): Product? =
        withContext(Dispatchers.IO) {
            try {
                val token = tokenProvider.getToken()
                val response =
                    traceStoreApiService.syncProductToBlockchain(
                        "Bearer $token",
                        productId
                    )

                if (response.isSuccessful && response.body()?.data != null) {
                    response.body()!!.data!!
                } else {
                    Log.e(
                        "ProductRepository",
                        "Blockchain sync failed: ${response.code()}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("ProductRepository", "syncProductToBlockchain Exception", e)
                null
            }
        }
}
