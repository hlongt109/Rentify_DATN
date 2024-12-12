package com.rentify.user.app.model.FakeModel

import com.mapbox.geojson.Point
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.RoomsResponse


data class MarkerInfo(
    val point: Point,
    val room : RoomResponse
)