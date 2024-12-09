package com.rentify.user.app.model.FakeModel

import com.mapbox.geojson.Point

data class MarkerInfo(
    val point: Point,
    val title: String,
    val description: String,
    val type: String? = null,
    val address: String? = null
)