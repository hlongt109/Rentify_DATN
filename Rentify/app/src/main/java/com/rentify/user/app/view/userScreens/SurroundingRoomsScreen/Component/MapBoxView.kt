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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mapbox.maps.MapView
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.animation.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.rentify.user.app.MainActivity
import com.rentify.user.app.R
import com.rentify.user.app.model.FakeModel.MarkerInfo
import com.rentify.user.app.network.GeocodingService
import com.rentify.user.app.repository.ListRoomMap.RoomData
import com.rentify.user.app.ui.theme.homeMarker
import com.rentify.user.app.ui.theme.home_marker
import com.rentify.user.app.ui.theme.location
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.utils.LocationUntil.checkLocationPermission
import com.rentify.user.app.utils.LocationUntil.getCurrentLocation
import com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.FakeMarker
import com.rentify.user.app.viewModel.UserViewmodel.AutofillViewModel
import com.rentify.user.app.viewModel.UserViewmodel.MapViewModel


@Composable
fun MapboxMapView(
    navController: NavController,
    viewModel: AutofillViewModel = viewModel(),
    listViewModel: MapViewModel = viewModel(),
    latitude: Double,
    longitude: Double,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val selectedLocation by viewModel.selectedLocation.observeAsState()
    var currentLocation by remember { mutableStateOf<Point?>(null) }

    var searchLocation = Point.fromLngLat(latitude, longitude)
    Log.d("SearchLocation", "MapboxMapView: $searchLocation")

    Log.d("TestToaDo", "MapBoxView: $longitude,$latitude")

    var cameraState by remember { mutableStateOf(CameraOptions.Builder().build()) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    var markers by remember { mutableStateOf(listOf<Point>()) }
    var currentAddress by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var searchResultLocation by remember { mutableStateOf<Point?>(null) }
    var selectedMarker by remember { mutableStateOf<RoomData?>(null) }
    var visibleAnimation by remember { mutableStateOf(false) }
    // Thay đổi danh sách markers
    val markerPoints = FakeMarker().fakeMarker

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
    //style map
    var currentMapStyle by remember {
        mutableStateOf(Style.MAPBOX_STREETS)
    }

    val mapboxAccessToken = context.getString(R.string.mapbox_access_token)

    // Hàm thay đổi style
    fun changeMapStyle() {
        if (mapView != null) {
            val currentIndex = FakeMarker().mapStyles.indexOf(currentMapStyle)
            val nextIndex = (currentIndex + 1) % FakeMarker().mapStyles.size
            currentMapStyle = FakeMarker().mapStyles[nextIndex]
            Log.d("CheckMapStyle", "changeMapStyle: $currentMapStyle")
            mapView.getMapboxMap().loadStyleUri(currentMapStyle)
        } else {
            Log.e("CheckMapStyle", "mapView is not initialized")
        }
    }

    val suggestion by viewModel.suggestions.collectAsState()
    val listRoom by listViewModel.listRoom.observeAsState()
    Log.d("ListRoomMap", "MapboxMapView: $listRoom")
    LaunchedEffect(Unit) {
        listViewModel.getListRoomMap()
    }
//    LaunchedEffect(searchQuery) {
//        viewModel.getSuggestions(searchQuery)
//    }
    Log.d("SelectedLocation", "MapboxMapView: $selectedLocation")
    var hasCameraMoved by remember { mutableStateOf(false) }
   if(!hasCameraMoved){
       if (longitude != 0.0 && latitude != 0.0) {
           // Di chuyển viewport đến vị trí hiện tại
           hasLocationPermission = true
           mapViewportState.setCameraOptions(
               CameraOptions.Builder()
                   .center(searchLocation)
                   .zoom(15.0)
                   .build()
           )
           hasCameraMoved = true
       } else {
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
                           hasCameraMoved = true
                           //goi api de lay vi tri
                           location?.let {
                               GeocodingService().reverseGeocode(context, it) { address ->
                                   Log.d("CheckAddress", "MapboxMapView: $location, $address")
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
       }
   }

    val pointString = "${currentLocation?.longitude()},${currentLocation?.latitude()}"
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
                mapState = mapviewState,

                ) {
                MapStyle(
                    style = currentMapStyle
                )
                val marker =
                    rememberIconImage(key = "keyMarker", painter = painterResource(home_marker))
                currentLocation?.let { point ->
//                    CircleAnnotation(point = point) {
//                        // Style the circle that will be added to the map.
//                        circleRadius = 100.0
//                        circleColor = Color(0x447ff59f)
//                        circleStrokeWidth = 2.0
//                        circleStrokeColor = Color(0xffffffff)
//                    }

                    MapEffect(currentMapStyle) { mapView ->
                        mapView.location.updateSettings {
                            locationPuck = createDefault2DPuck(withBearing = true)
                            enabled = true
                            puckBearing = PuckBearing.COURSE
                            puckBearingEnabled = true
                        }
                        mapViewportState.transitionToFollowPuckState()
                    }
                }

                if(longitude != 0.0 && latitude != 0.0 && searchLocation != null){
                    searchLocation?.let { point ->
                        val searchMarker = rememberIconImage(key = "searchMarker", painter = painterResource(location))
                        PointAnnotation(
                            point = point,
                        ) {
                            iconImage = searchMarker
                            iconSize = 2.0
                            textOffset = listOf(0.0, -2.0)
                            textHaloColor = Color.White
                            textHaloWidth = 2.0
                        }
                    }
                }
                listRoom?.forEach { point ->
                    Log.d("CheckPoint", "MapboxMapView: ${point.building.coordinates}")
                    val data = point.building
                    var pointMarker by remember { mutableStateOf<Point?>(null) }
                    if (!data.coordinates.isNullOrEmpty() && data.coordinates.size >= 2) {
                        pointMarker = Point.fromLngLat(data.coordinates[1], data.coordinates[0])
                        Log.d("PointMarker", "MapboxMapView: $pointMarker")
                        // Tiếp tục xử lý nếu cần
                    } else {
                        // Không thực hiện gán pointMarker hoặc bỏ qua hiển thị marker
                        println("Coordinates trống hoặc không hợp lệ.")
                    }
                    pointMarker?.let {
                        PointAnnotation(
                            point = it,
                            onClick = { annotation ->
                                selectedMarker = point
                                visibleAnimation = true
                                true // Trả về true để ngăn sự kiện lan sang các đối tượng khác
                            }
                        ) {
                            iconImage = marker
                            iconSize = 2.0
                            textField =
                                CheckUnit.formattedPrice(point.representativeRoom.price.toFloat())
                            textOffset = listOf(0.0, -2.0)
                            textHaloColor = Color.White
                            textHaloWidth = 2.0

                        }
                    }
                }

            }
            AnimatedVisibility(
                visible = visibleAnimation, // Thay đổi điều kiện này
                enter = slideInVertically(
                    initialOffsetY = { height -> height },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300)),
                exit = slideOutVertically(
                    targetOffsetY = { height -> height },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300)),
                modifier = Modifier.zIndex(10000f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .zIndex(10000f)
                        .padding(bottom = 15.dp)
                ) {
                    selectedMarker.let { marker ->
                        if (marker != null) {
                            ItemClickedMarker(
                                onClose = {
                                    selectedMarker = null
                                    visibleAnimation = false
                                },
                                room = marker.representativeRoom,
                                navController = navController
                            )
                        }
                    }
                }
            }
            // Thanh tìm kiếm
            Box(
                modifier = Modifier
                    .zIndex(1000f)
                    .padding(10.dp)
                    .offset(y = -screenHeight.dp / 1.26f)
                    .clip(RoundedCornerShape(15.dp))
            ) {
                TextFieldMapSearch(
                    placeholder = currentAddress,
                    value = searchQuery,
                    onValueChange = { newSearch ->
//                        searchQuery = newSearch
//                        Log.d("CheckSearch2", "MapboxMapView: $searchQuery")
//                        viewModel.getSuggestions(searchQuery)
                    },
                    isFocused = remember { mutableStateOf(false) },
                    onSearch = {
                    },
                    onClick = { navController.navigate(MainActivity.ROUTER.SEARCHMAP.name) },
                    enable = false
                )
//
            }


            // Button lấy vị trí hiện tại
            Box(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 15.dp)
                    .zIndex(100f)
            ) {
                Column {

                    ChangeStyleMap(
                        currentStyle = currentMapStyle,
                        onStyleChange = ::changeMapStyle
                    )
                    Spacer(
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
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
}