package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CircleOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.geojson.Point
import com.mapbox.maps.Map
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.animation.*
import com.rentify.user.app.network.GeocodingService
import com.rentify.user.app.utils.LocationUntil.checkLocationPermission
import com.rentify.user.app.utils.LocationUntil.getCurrentLocation


@Composable
fun MapboxMapView() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    var currentLocation by remember { mutableStateOf<Point?>(null) }

    var cameraState by remember { mutableStateOf(CameraOptions.Builder().build()) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    var markers by remember { mutableStateOf(listOf<Point>()) }
    var currentAddress by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    val markerPoints = listOf(
        Point.fromLngLat(106.660172, 10.762622),
        Point.fromLngLat(106.660535, 10.762535),
        Point.fromLngLat(-122.0786, 37.3947)
    )

    val mapViewportState = rememberMapViewportState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
    }

    val mapView = remember {
        MapView(context).apply {
            // Kích hoạt LocationComponent để hiển thị vị trí người dùng
            getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        }
    }
    LaunchedEffect(Unit) {
        checkLocationPermission(
            context,
            onPermissionGranted = {
                hasLocationPermission = true
                getCurrentLocation(context) { location ->
                    currentLocation = location

                    // Di chuyển viewport đến vị trí hiện tại
                    mapViewportState.setCameraOptions(
                        CameraOptions.Builder()
                            .center(currentLocation)
                            .zoom(15.0)
                            .build()
                    )

                    //goi api de lay vi tri
                    location?.let {
                        GeocodingService().reverseGeocode(context,it){address ->
                            currentAddress = address ?: "Không tìm thấy vị trí"
                        }
                    }
                }
            },
            onPermissionDenied = {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        )
    }

    if (!hasLocationPermission) {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NotPermissionPosition(
                context = context,
                permissionLauncher = permissionLauncher
            )
        }
    } else {
        Box(contentAlignment = Alignment.BottomEnd) {
            MapboxMap(
                Modifier.fillMaxSize(),
                mapViewportState = mapViewportState,
            ) {
                MapEffect(Unit) { mapView ->
                    mapView.location.updateSettings {
                        locationPuck = createDefault2DPuck(withBearing = true)
                        enabled = true
                        puckBearing = PuckBearing.COURSE
                        puckBearingEnabled = true
                    }
                    mapViewportState.transitionToFollowPuckState()
                }
            }
            // Thanh tìm kiếm
            Box(
                modifier = Modifier
                    .zIndex(1000f)
                    .padding(10.dp)
                    .offset(y = -screenHeight.dp / 1.25f)
            ) {
                TextFieldMapSearch(
                    placeholder = "Tìm kiếm vị trí",
                    value = if(searchQuery.isEmpty()) currentAddress else searchQuery,
                    onValueChange = { newSearch ->
                        searchQuery = newSearch
                        currentAddress = ""
                        // Tìm kiếm vị trí trên Mapbox
//                    searchLocation(newSearch, context) { location ->
//                        location?.let {
//                            currentLocation = Point.fromLngLat(it.longitude, it.latitude)
//                            mapView.getMapboxMap().setCamera(CameraOptions.Builder()
//                                .center(currentLocation)
//                                .zoom(15.0)
//                                .build())
//                        }
//                    }
                    },
                    isFocused = remember { mutableStateOf(false) }
                )
            }

            // Button lấy vị trí hiện tại
            Box(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 15.dp)
                    .zIndex(1000f)
            ) {
                PositionButton(onClick = {
                    getCurrentLocation(context) { location ->
                        currentLocation = location

                        // Di chuyển viewport đến vị trí hiện tại
                        mapViewportState.easeTo(
                            CameraOptions.Builder()
                                .center(currentLocation)
                                .zoom(15.0)
                                .build(),
                            MapAnimationOptions.Builder()
                                .duration(1500)
                                .build()
                        )
                    }
                })

            }
        }
    }
}
