package com.rentify.user.app.network.ApiStaff

import com.rentify.user.app.network.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitStaffService {
    // Tạo logging interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Tạo OkHttpClient với logging
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/api/staff/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val ApiService: ApiServiceStaff = retrofit.create(ApiServiceStaff::class.java)

    private val retrofit_: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val ApiService_ : ApiServiceStaff by lazy{
        retrofit_.create(ApiServiceStaff::class.java)
    }
}