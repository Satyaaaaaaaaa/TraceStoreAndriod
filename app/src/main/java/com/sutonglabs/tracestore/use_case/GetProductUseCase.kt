package com.sutonglabs.tracestore.use_case

import com.sutonglabs.tracestore.common.Resource
import com.sutonglabs.tracestore.models.ProductResponse
import com.sutonglabs.tracestore.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<ProductResponse>> = flow {
        try {
            emit(Resource.Loading())
            val products = repository.getProduct()
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
