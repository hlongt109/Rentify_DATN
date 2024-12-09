package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import android.Manifest
import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CircleOptions
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.MapView
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.Map
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.properties.generated.CirclePitchScale
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.animation.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.renderer.widget.WidgetPosition
import com.rentify.user.app.model.FakeModel.MarkerInfo
import com.rentify.user.app.network.GeocodingService
import com.rentify.user.app.ui.theme.homeMarker
import com.rentify.user.app.utils.LocationUntil.checkLocationPermission
import com.rentify.user.app.utils.LocationUntil.getCurrentLocation
import com.rentify.user.app.utils.LocationUntil.isPointInCircle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


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
    var selectedMarker by remember { mutableStateOf<MarkerInfo?>(null) }
    // Thay đổi danh sách markers
    val markerPoints = listOf(
        MarkerInfo(
            point = Point.fromLngLat(106.660172, 10.762622),
            title = "Địa điểm 1",
            description = "Mô tả chi tiết về địa điểm 1",
            type = "Nhà hàng",
            address = "123 Đường ABC, Quận 1, TP.HCM"
        ),
        MarkerInfo(
            point = Point.fromLngLat(106.660535, 10.762535),
            title = "Địa điểm 2",
            description = "Mô tả chi tiết về địa điểm 2",
            type = "Quán cà phê",
            address = "456 Đường XYZ, Quận 3, TP.HCM"
        ),
        MarkerInfo(
            point = Point.fromLngLat(-122.0786, 37.3947),
            title = "Địa điểm 3",
            description = "Mô tả chi tiết về địa điểm 3",
            type = "Văn phòng",
            address = "Google Campus, Mountain View, CA"
        ),
        MarkerInfo(
            point = Point.fromLngLat(-122.0765, 37.3984),
            title = "Địa điểm 4",
            description = "Mô tả chi tiết về địa điểm 4",
            type = "Văn phòng",
            address = "Google Campus, Mountain View, CA"
        )
    )

    val mapViewportState = rememberMapViewportState()
    val mapviewState = rememberMapState {
        gesturesSettings = GesturesSettings {
            pitchEnabled = true
            doubleTapToZoomInEnabled = true
            doubleTouchToZoomOutEnabled = true
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("CheckPermisstion", "MapboxMapView: #$isGranted")
        hasLocationPermission = isGranted
    }

    val mapView = remember {
        MapView(context).apply {
            // Kích hoạt LocationComponent để hiển thị vị trí người dùng
            getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        }
    }

    val bitMapIcon = BitmapFactory.decodeResource(context.resources, homeMarker)
    mapView.getMapboxMap().getStyle { style ->
        style.addImage("custom-marker", bitMapIcon)
    }
    if (bitMapIcon == null) {
        Log.e("CheckImageMarker", "Bitmap không được tạo thành công")
    } else {
        Log.d("CheckImageMarker", "Bitmap đã được tạo: $bitMapIcon")
    }


    //tao pointAnnotationManager
    val pointAnnotationManager by remember {
        mutableStateOf(mapView.annotations.createPointAnnotationManager())
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
                        GeocodingService().reverseGeocode(context, it) { address ->
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
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NotPermissionPosition(
                context = context,
                permissionLauncher = permissionLauncher
            )
        }
    } else {

        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.fillMaxSize()
        ) {
            MapboxMap(
                Modifier.fillMaxSize(),
                mapViewportState = mapViewportState,
                mapState = mapviewState
            ) {
                val marker =
                    rememberIconImage(key = "keyMarker", painter = painterResource(homeMarker))
                currentLocation?.let { point ->
                    CircleAnnotation(point = point) {
                        // Style the circle that will be added to the map.
                        circleRadius = 100.0
                        circleColor = Color(0x447ff59f)
                        circleStrokeWidth = 2.0
                        circleStrokeColor = Color(0xffffffff)
                    }

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

                markerPoints.forEach { point ->
                    Log.d("CheckPoint", "MapboxMapView: $point")
                    Log.d("CheckPoint", "Marker trong vòng tròn: $point")
                    PointAnnotation(
                        point = point.point,
                        onClick = { annotation ->
                            // Xử lý sự kiện click
                            Log.d("MarkerClick", "Clicked: ${point.title}")
                            selectedMarker = point
                            true // Trả về true để ngăn sự kiện lan sang các đối tượng khác
                        }
                    ) {
                        iconImage = marker
                        iconSize = 0.2
                        textField = point.title
                        textOffset = listOf(0.0, -2.0)
                        textHaloColor = Color.White
                        textHaloWidth = 2.0

                    }
                }
            }

            selectedMarker?.let { marker ->
                Box(
                    modifier = Modifier
                        .zIndex(1000f)
                        .padding(bottom = 15.dp)
                ) {
                    ItemClickedMarker(item = marker)
                }
            }
            // Thanh tìm kiếm
            Box(
                modifier = Modifier
                    .zIndex(1000f)
                    .padding(10.dp)
                    .offset(y = -screenHeight.dp / 1.26f)
            ) {
                TextFieldMapSearch(
                    placeholder = currentAddress,
                    value = searchQuery,
                    onValueChange = { newSearch ->
                        searchQuery = newSearch

                    },
                    isFocused = remember { mutableStateOf(false) }
                )
            }

            // Button lấy vị trí hiện tại
            Box(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 15.dp)
                    .zIndex(100f)
            ) {
                PositionButton(onClick = {
                    getCurrentLocation(context) { location ->
                        currentLocation = location
                        Log.d("CheckLocation", "MapboxMapView: $location")
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