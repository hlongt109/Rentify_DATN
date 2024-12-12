package com.rentify.user.app.network

import android.content.Context
import com.rentify.user.app.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class GeocodingHelper {

    private val client = OkHttpClient()


    fun forwardGeocode(context: Context,query: String, callback: (String?) -> Unit) {
        val accessToken = context.getString(R.string.mapbox_access_token)
        val url = "https://api.mapbox.com/geocoding/v5/mapbox.places/${query}.json?access_token=$accessToken"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val jsonObject = JSONObject(it)
                    callback(jsonObject.toString())
                }
            }
        })
    }
}
