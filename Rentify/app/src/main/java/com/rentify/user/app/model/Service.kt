package com.rentify.user.app.model

import android.os.Parcelable
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