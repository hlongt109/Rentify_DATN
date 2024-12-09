package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import androidx.compose.runtime.Composable
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource

//@Composable
//fun MarkerMapbox(point: Point, title: String) {
//    MapEffect(point) { mapView ->
//        val style = mapView.getMapboxMap().getStyle() ?: return@MapEffect
//
//        // Thêm marker mới
//        val markerSource = GeoJsonSource(
//            id = title,
//            feature = Feature.fromGeometry(point)
//        )
//
//        style.addSource(markerSource)
//
//        val markerLayer = SymbolLayer(
//            id = title,
//            sourceId = title
//        )
//        markerLayer.iconImage("marker-15")
//        markerLayer.iconSize(1.5)
//        markerLayer.iconAllowOverlap(true)
//
//        style.addLayer(markerLayer)
//    }
//}