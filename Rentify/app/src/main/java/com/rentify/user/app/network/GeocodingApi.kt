package com.rentify.user.app.network

import android.content.Context
import com.mapbox.geojson.Point
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("geocoding/v5/mapbox.places/{query}.json")
    fun searchLocation(
        @Query("query") query: String,
        @Query("access_token") accessToken: String
    ): Call<GeocodingResponse>
}
data class Geometry(
    val coordinates: List<Double>
)
