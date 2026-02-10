package com.sutonglabs.tracestore.di

import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.repository.AssetRepository
import com.sutonglabs.tracestore.use_case.ProcessQrScanUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object QrModule {

    @Provides
    fun provideAssetRepository(
        api: TraceStoreAPI
    ): AssetRepository {
        return AssetRepository(api)
    }

    @Provides
    fun provideProcessQrScanUseCase(
        repository: AssetRepository
    ): ProcessQrScanUseCase {
        return ProcessQrScanUseCase(repository)
    }
}

