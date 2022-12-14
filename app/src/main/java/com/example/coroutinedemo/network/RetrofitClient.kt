package com.androidmvvmdatabindingrecyclerviewkotlin.network

import android.util.Log
import com.example.coroutinedemo.network.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
//    private val BASE_URL = "https://quotable.io/"
    private val BASE_URL = "https://444.io/"
    private var retrofit: Retrofit? = null

    val service: ApiInterface
        get() {
            retrofit =null
            if (retrofit == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL) .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                Log.d("222","~~inside~~")
            }

            return retrofit!!.create<ApiInterface>(ApiInterface::class.java!!)
        }

}