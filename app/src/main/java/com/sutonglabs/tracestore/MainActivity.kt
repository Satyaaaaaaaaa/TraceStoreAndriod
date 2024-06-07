package com.sutonglabs.tracestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sutonglabs.tracestore.api.TraceStoreAPI
import com.sutonglabs.tracestore.models.ProductResponse
import com.sutonglabs.tracestore.ui.login.LoginScreen
import com.sutonglabs.tracestore.ui.theme.TraceStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiService: TraceStoreAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchProducts()
        setContent {
            TraceStoreTheme {
                LoginScreen()
            }
        }
    }

    private  fun fetchProducts() {
        val call = apiService.getProducts()

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val products = response.body()?.data
                    products?.forEach {
                        Log.d("MainActivity", "Product: ${it.name}, Price: ${it.price}")
                    }
                } else {
                    Log.e("MainActivity", "Response failed with status: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("MainActivity", "Network request failed: ${t.message}")
            }
        })
    }

}


