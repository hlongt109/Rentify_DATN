package com.rentify.user.app.network

import android.net.Uri
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.network.LocationService.ApiService
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList

import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

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
        .baseUrl("http://192.168.2.106:3000/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val ApiService: APIService = retrofit.create(APIService::class.java)
}

object LocationService{
    val location: Retrofit = Retrofit.Builder()
        .baseUrl("https://provinces.open-api.vn/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val ApiService: APIService = location.create(APIService::class.java)
}

object ApiClient {
    private const val BASE_URL = "http://192.168.2.106:3000/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: APIService = retrofit.create(APIService::class.java)
}
object RetrofitClient {
    private const val BASE_URL = "http://192.168.2.106:3000/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: APIService = retrofit.create(APIService::class.java)
}

