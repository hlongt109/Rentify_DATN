package com.rentify.user.app.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    // Tạo logging interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Tạo OkHttpClient với logging
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.53.105:3000/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val ApiService: APIService = retrofit.create(APIService::class.java)
}
