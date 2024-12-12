package com.rentify.user.app.network

import android.content.Context
import com.mapbox.geojson.Point
import com.rentify.user.app.R
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class SearchService{
    fun searchLocation(context: Context, query: String, onResult: (Point?, String?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(GeocodingApi::class.java)
        val accessToken = context.getString(R.string.mapbox_access_token)

        api.searchLocation(query, accessToken).enqueue(object : Callback<GeocodingResponse> {
            override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
                val location = response.body()?.features?.firstOrNull()
                location?.let {
                    val point = Point.fromLngLat(it.geometry.coordinates[0], it.geometry.coordinates[1])
                    onResult(point, it.place_name)
                } ?: onResult(null, null)
            }

            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                onResult(null, null)
            }
        })
    }
}