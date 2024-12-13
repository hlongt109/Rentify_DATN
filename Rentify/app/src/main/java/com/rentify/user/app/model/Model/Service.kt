package com.rentify.user.app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    val name: String,
    val description: String,
    val price: Double,
    val photos: List<String>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
): Parcelable

data class ListServiceResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("description")val description: String,
    @SerializedName("landlord_id")val landlord_id: String,
    @SerializedName("name")val name: String,
    @SerializedName("photos")val photos: List<String>,
    @SerializedName("price")val price: Int,
    @SerializedName("updated_at")val updated_at: String
)

