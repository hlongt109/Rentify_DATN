package com.rentify.user.app.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.mapbox.geojson.Point
import com.rentify.user.app.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class GeocodingService {
    private val client = OkHttpClient()

    fun reverseGeocode(context: Context,point: Point, callback: (String?) -> Unit) {
        val accessToken = context.getString(R.string.mapbox_access_token)
        val url = "https://api.mapbox.com/geocoding/v5/mapbox.places/${point.longitude()},${point.latitude()}.json?access_token=$accessToken"

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val geocodingResponse = Gson().fromJson(it, GeocodingResponse::class.java)
                    val address = geocodingResponse.features.firstOrNull()?.placeName
                    callback(address)
                }
            }
        })
    }
}
data class GeocodingResponse(
    val features: List<Feature>
)

data class Feature(
    @SerializedName("place_name") val placeName: String
)