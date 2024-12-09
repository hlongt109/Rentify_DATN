package com.rentify.user.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Response
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.rentify.user.app.R
import com.rentify.user.app.network.GeocodingResponse
import okhttp3.Call
import okhttp3.Callback
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object LocationUntil {
    // Modify these functions to work with Mapbox
    fun checkLocationPermission(
        context: Context,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            else -> {
                onPermissionDenied()
            }
        }
    }

    fun getCurrentLocation(
        context: Context,
        onLocationUpdated: (Point) -> Unit
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val point = Point.fromLngLat(it.longitude, it.latitude)
                        onLocationUpdated(point)
                    }
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    fun calculateDistance(point1: Point, point2: Point): Double {
        val R = 6371000 // Bán kính Trái Đất tính bằng mét
        val lat1 = Math.toRadians(point1.latitude())
        val lon1 = Math.toRadians(point1.longitude())
        val lat2 = Math.toRadians(point2.latitude())
        val lon2 = Math.toRadians(point2.longitude())

        val dLat = lat2 - lat1
        val dLon = lon2 - lon1

        val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }


    // Modify search location to return Mapbox Point
//    fun searchLocation(
//        query: String,
//        onResult: (Point?) -> Unit,
//        context: Context
//    ) {
//        // Similar to previous implementation, but convert to Mapbox Point
//        val client = MapboxGeocoding.builder()
//            .accessToken(context.getString(R.string.mapboxtoken))
//            .query(query)
//            .build()
//
//        client.enqueue(object : Callback<GeocodingResponse> {
//            override fun onResponse(
//                call: Call<GeocodingResponse>,
//                response: Response<GeocodingResponse>
//            ) {
//                val location = response.body()?.features()?.firstOrNull()?.center()
//                location?.let {
//                    onResult(it)
//                } ?: onResult(null)
//            }
//
//            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
//                onResult(null)
//            }
//        })
//    }
}