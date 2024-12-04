package com.sutonglabs.tracestore.repository

import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.models.ProductResponse
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import retrofit2.await
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val traceStoreApiService: TraceStoreAPI
) : ProductRepository {

    override suspend fun getProduct(): ProductResponse {
        return withContext(Dispatchers.IO) {
            traceStoreApiService.getProducts().await()
        }
    }
//    override suspend fun getProductDetail(id: Int): Product {
//        return traceStoreApiService.getProducts()[id-1]
//    }
}