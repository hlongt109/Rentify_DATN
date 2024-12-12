package com.rentify.user.app.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.geojson.Point
import com.rentify.user.app.R
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class GeocodingService {
    private val client = OkHttpClient()

//    fun reverseGeocode(context: Context,point: Point, callback: (String?) -> Unit) {
//        val accessToken = context.getString(R.string.mapbox_access_token)
//        val url = "https://api.mapbox.com/geocoding/v5/mapbox.places/${point.longitude()},${point.latitude()}.json?access_token=$accessToken"
//
//        val request = Request.Builder().url(url).build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//                callback(null)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.body?.string()?.let {
//                    val geocodingResponse = Gson().fromJson(it, GeocodingResponse::class.java)
//                    val address = geocodingResponse.features.firstOrNull()?.place_name
//                    callback(address)
//                }
//            }
//        })
//    }
fun reverseGeocode(context: Context, point: Point, callback: (String?) -> Unit) {
    val accessToken = context.getString(R.string.mapbox_access_token)
    val geocoder = MapboxGeocoding.builder()
        .accessToken(accessToken)
        .query(Point.fromLngLat(point.longitude(), point.latitude()))
        .build()

    geocoder.enqueueCall(object : Callback<GeocodingResponse> {
        override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
            val result = response.body()?.features()
            val address = result?.firstOrNull()?.placeName()
            callback(address)
        }

        override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
            t.printStackTrace()
            callback(null)
        }
    })
}

}
data class GeocodingResponse(
    val features: List<Feature>
)

data class Feature(
    val place_name: String,
    val geometry: Geometry
)